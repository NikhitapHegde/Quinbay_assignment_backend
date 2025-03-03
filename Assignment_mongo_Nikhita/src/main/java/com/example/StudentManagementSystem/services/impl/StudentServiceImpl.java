package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.entity.StudentCourse;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentCourseRepository;
import com.example.StudentManagementSystem.repository.StudentRpository;
import com.example.StudentManagementSystem.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRpository studentRpository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public Student getOne(String id) {
        logger.info("Fetching student with ID: {}", id);
        return studentRpository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRpository.findAll();
    }

    @Override
    public Student insertOrUpdate(Student student, List<String> courseIds, String organizationId) {
        logger.info("Attempting to save student: {}", student);
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    logger.error("Organization with ID {} not found", organizationId);
                    return new RuntimeException("Organization Not Found");
                });

        student.setOrganization(organization);

        if (student.getId() != null) {
            student = studentRpository.findById(student.getId()).orElse(student);
        }

        Student managedStudent = studentRpository.save(student);
        logger.info("Student saved successfully with ID: {}", managedStudent.getId());

        if (courseIds != null && !courseIds.isEmpty()) {
            List<Course> courses = StreamSupport.stream(courseRepository.findAllById(courseIds).spliterator(), false)
                    .collect(Collectors.toList());

            List<StudentCourse> studentCourses = courses.stream().map(course -> {
                StudentCourse sc = new StudentCourse();
                sc.setStudent(managedStudent);
                sc.setCourse(course);
                sc.setStatus("TO_DO");
                return sc;
            }).collect(Collectors.toList());

            studentCourseRepository.saveAll(studentCourses);
            student.setStudentCourse(studentCourses);
            logger.info("Enrolled student {} in {} courses", managedStudent.getId(), studentCourses.size());
        }
        return student;
    }

    @Override
    public boolean deleteStudent(String id) {
        logger.info("Deleting student with ID: {}", id);
        studentRpository.deleteById(id);
        boolean exists = studentRpository.findById(id).isPresent();
        if (!exists) {
            logger.info("Student with ID {} deleted successfully", id);
        } else {
            logger.error("Failed to delete student with ID {}", id);
        }
        return !exists;
    }

    @Override
    public boolean deleteAllStudent() {
        logger.info("Deleting all students");
        studentRpository.deleteAll();
        boolean isEmpty = studentRpository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All students deleted successfully");
        } else {
            logger.error("Failed to delete all students");
        }
        return isEmpty;
    }

    @Override
    public boolean enrollToCourse(String studentId, String courseId) {
        logger.info("Enrolling student {} to course {}", studentId, courseId);

        Student student = studentRpository.findById(studentId).orElseThrow(() -> {
            logger.error("Student with ID {} not found", studentId);
            return new RuntimeException("Student Not Found");
        });

        Course course = courseRepository.findById(courseId).orElseThrow(() -> {
            logger.error("Course with ID {} not found", courseId);
            return new RuntimeException("Course Not Found");
        });

        boolean alreadyEnrolled = student.getStudentCourse() != null &&
                student.getStudentCourse().stream().anyMatch(sc -> sc.getCourse().getId().equals(courseId));

        if (alreadyEnrolled) {
            logger.warn("Student {} is already enrolled in course {}", studentId, courseId);
            return false;
        }

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setStatus("TO_DO");

        student.getStudentCourse().add(studentCourse);
        studentCourseRepository.save(studentCourse);
        studentRpository.save(student);

        logger.info("Student {} enrolled in course {} successfully", studentId, courseId);
        return true;
    }

    @Override
    public boolean withdrawFromCourse(String studentId, String courseId) {
        logger.info("Withdrawing student {} from course {}", studentId, courseId);

        Student student = studentRpository.findById(studentId).orElseThrow(() -> {
            logger.error("Student with ID {} not found", studentId);
            return new RuntimeException("Student Not Found");
        });

        Course course = courseRepository.findById(courseId).orElseThrow(() -> {
            logger.error("Course with ID {} not found", courseId);
            return new RuntimeException("Course Not Found");
        });

        boolean isEnrolled = student.getStudentCourse().stream().anyMatch(sc -> sc.getCourse().getId().equals(courseId));

        if (!isEnrolled) {
            logger.warn("Student {} is not enrolled in course {}", studentId, courseId);
            return false;
        }

        student.getStudentCourse().removeIf(sc -> sc.getCourse().getId().equals(courseId));
        studentRpository.save(student);

        logger.info("Student {} successfully withdrawn from course {}", studentId, courseId);
        return true;
    }

    public boolean updateCourseStatus(String studentId, String courseId, String status) {
        logger.info("Updating course status for student {} in course {} to {}", studentId, courseId, status);

        Optional<StudentCourse> studentCourseOpt = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (studentCourseOpt.isPresent()) {
            StudentCourse studentCourse = studentCourseOpt.get();

            if (status.equalsIgnoreCase("TO-DO") || status.equalsIgnoreCase("IN-PROGRESS") || status.equalsIgnoreCase("COMPLETED")) {
                studentCourse.setStatus(status);
                studentCourseRepository.save(studentCourse);
                logger.info("Course status updated successfully for student {} in course {} to {}", studentId, courseId, status);
                return true;
            } else {
                logger.warn("Invalid status '{}' provided for student {} in course {}", status, studentId, courseId);
                return false;
            }
        } else {
            logger.error("Student {} is not enrolled in course {}", studentId, courseId);
            return false;
        }
    }

    public List<StudentCourse> getStudentCourseProgress(String studentId) {
        logger.info("Fetching course progress for student {}", studentId);
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        return studentCourses.stream()
                .map(course -> new StudentCourse(course.getId(), course.getStatus()))
                .collect(Collectors.toList());
    }

    public List<StudentCourse> getStudentByProgress(String studentStatus) {
        logger.info("Fetching students with course status {}", studentStatus);
        return studentCourseRepository.findByStatus(studentStatus);
    }
}