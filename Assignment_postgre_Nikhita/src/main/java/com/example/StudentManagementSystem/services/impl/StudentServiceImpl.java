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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Student getOne(Long id) {
        logger.info("Fetching student with ID: {}", id);
        return studentRpository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRpository.findAll();
    }

    @Override
    public Student insertOrUpdate(Student student, List<Long> courseIds, Long organizationId) {
        logger.info("Inserting/Updating student: {}", student);
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
        logger.info("Saved student: {}", managedStudent);

        if (courseIds != null && !courseIds.isEmpty()) {
            List<Course> courses = courseRepository.findAllById(courseIds);

            List<StudentCourse> studentCourses = courses.stream().map(course -> {
                StudentCourse sc = new StudentCourse();
                sc.setStudent(managedStudent);
                sc.setCourse(course);
                sc.setStatus("TO_DO");
                return sc;
            }).collect(Collectors.toList());

            studentCourseRepository.saveAll(studentCourses);
            student.setStudentCourse(studentCourses);
            logger.info("Assigned courses {} to student {}", courseIds, student.getId());
        }

        return student;
    }

    @Override
    public boolean deleteStudent(Long id) {
        logger.info("Deleting student with ID: {}", id);
        studentRpository.deleteById(id);
        boolean exists = studentRpository.findById(id).isPresent();
        logger.info("Student with ID {} deleted: {}", id, !exists);
        return !exists;
    }

    @Override
    public boolean deleteAllStudent() {
        logger.info("Deleting all students");
        studentRpository.deleteAll();
        boolean isEmpty = studentRpository.findAll().isEmpty();
        logger.info("All students deleted: {}", isEmpty);
        return isEmpty;
    }

    @Override
    public boolean enrollToCourse(Long studentId, Long courseId) {
        logger.info("Enrolling student ID {} to course ID {}", studentId, courseId);
        Student student = studentRpository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student with ID {} not found", studentId);
                    return new RuntimeException("Student Not Found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course with ID {} not found", courseId);
                    return new RuntimeException("Course Not Found");
                });

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setStatus("TO_DO");

        student.getStudentCourse().add(studentCourse);
        studentCourseRepository.save(studentCourse);
        studentRpository.save(student);
        logger.info("Student ID {} enrolled in course ID {}", studentId, courseId);
        return true;
    }

    @Override
    public boolean withdrawFromCourse(Long studentId, Long courseId) {
        logger.info("Withdrawing student ID {} from course ID {}", studentId, courseId);
        Student student = studentRpository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student with ID {} not found", studentId);
                    return new RuntimeException("Student Not Found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course with ID {} not found", courseId);
                    return new RuntimeException("Course Not Found");
                });

        boolean exists = student.getStudentCourse().stream()
                .anyMatch(studentCourse -> studentCourse.getCourse().equals(course));

        if (!exists) {
            logger.warn("Student ID {} is not enrolled in course ID {}", studentId, courseId);
            return false;
        }

        student.getStudentCourse().removeIf(sc -> sc.getCourse().equals(course));
        studentRpository.save(student);
        logger.info("Student ID {} withdrawn from course ID {}", studentId, courseId);
        return true;
    }

    public boolean updateCourseStatus(Long studentId, Long courseId, String status) {
        logger.info("Updating course status for student ID {} and course ID {} to {}", studentId, courseId, status);
        Optional<StudentCourse> studentCourseOpt = studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (studentCourseOpt.isPresent()) {
            StudentCourse studentCourse = studentCourseOpt.get();

            if (status.equalsIgnoreCase("TO-DO") ||
                    status.equalsIgnoreCase("IN-PROGRESS") ||
                    status.equalsIgnoreCase("COMPLETED")) {

                studentCourse.setStatus(status);
                studentCourseRepository.save(studentCourse);
                logger.info("Updated course status for student ID {} and course ID {} to {}", studentId, courseId, status);
                return true;
            } else {
                logger.warn("Invalid course status {} provided for student ID {} and course ID {}", status, studentId, courseId);
                return false;
            }
        } else {
            logger.warn("StudentCourse entry not found for student ID {} and course ID {}", studentId, courseId);
            return false;
        }
    }

    public List<StudentCourse> getStudentCourseProgress(Long studentId) {
        logger.info("Fetching course progress for student ID {}", studentId);
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        logger.info("Fetched {} course progress entries for student ID {}", studentCourses.size(), studentId);
        return studentCourses.stream()
                .map(course -> new StudentCourse(course.getId(), course.getStatus()))
                .collect(Collectors.toList());
    }
}