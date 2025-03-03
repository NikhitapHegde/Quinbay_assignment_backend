package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.services.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InstructorServiceImpl implements InstructorService {

    private static final Logger logger = LoggerFactory.getLogger(InstructorServiceImpl.class);

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Instructor getOne(String id) {
        logger.info("Fetching instructor with ID: {}", id);
        return instructorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        return instructorRepository.findAll();
    }

    @Override
    public Instructor insertOrUpdate(Instructor instructor, String organizationId) {
        logger.info("Inserting/Updating instructor: {}, Organization ID: {}", instructor, organizationId);

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    logger.error("Organization not found with ID: {}", organizationId);
                    return new RuntimeException("Organization Not Found");
                });

        instructor.setOrganization(organization);
        Instructor savedInstructor = instructorRepository.save(instructor);

        logger.info("Instructor saved successfully with ID: {}", savedInstructor.getId());
        return savedInstructor;
    }

    @Override
    public boolean deleteInstructor(String id) {
        logger.info("Deleting instructor with ID: {}", id);

        if (!instructorRepository.existsById(id)) {
            logger.warn("Instructor with ID {} does not exist", id);
            return false;
        }

        instructorRepository.deleteById(id);
        boolean deleted = !instructorRepository.existsById(id);

        if (deleted) {
            logger.info("Instructor with ID {} deleted successfully", id);
        } else {
            logger.error("Failed to delete instructor with ID {}", id);
        }

        return deleted;
    }

    @Override
    public boolean deleteAllInstructors() {
        logger.info("Deleting all instructors");
        instructorRepository.deleteAll();

        boolean isEmpty = instructorRepository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All instructors deleted successfully");
        } else {
            logger.error("Failed to delete all instructors");
        }

        return isEmpty;
    }

    public boolean enrollToCourse(String instructorId, String courseId) {
        logger.info("Enrolling instructor with ID: {} to course with ID: {}", instructorId, courseId);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor not found with ID: {}", instructorId);
                    return new RuntimeException("Instructor not found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new RuntimeException("Course not found");
                });

        if (course.getInstructor() != null && instructor.getCourse().getId().equals(courseId)) {
            logger.warn("Instructor with ID {} is already enrolled in course with ID {}", instructorId, courseId);
            throw new RuntimeException("Instructor already enrolled to another course");
        }

        instructor.setCourse(course);
        instructorRepository.save(instructor);

        logger.info("Instructor with ID {} successfully enrolled to course with ID {}", instructorId, courseId);
        return true;
    }

    public boolean withdrawFromCourse(String instructorId, String courseId) {
        logger.info("Withdrawing instructor with ID: {} from course with ID: {}", instructorId, courseId);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor not found with ID: {}", instructorId);
                    return new RuntimeException("Instructor not found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with ID: {}", courseId);
                    return new RuntimeException("Course not found");
                });

        if (!Objects.equals(instructor.getCourse().getId(), courseId)) {
            logger.warn("Instructor with ID {} is not enrolled in course with ID {}", instructorId, courseId);
            return false;
        }

        instructor.setCourse(null);
        course.setInstructor(null);

        instructorRepository.save(instructor);
        courseRepository.save(course);

        logger.info("Instructor with ID {} successfully withdrew from course with ID {}", instructorId, courseId);
        return true;
    }
}