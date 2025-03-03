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
    public Instructor getOne(Long id) {
        logger.info("Fetching instructor with ID: {}", id);
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor == null) {
            logger.warn("Instructor with ID {} not found", id);
        } else {
            logger.debug("Instructor details: {}", instructor);
        }
        return instructor;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        List<Instructor> instructors = instructorRepository.findAll();
        logger.debug("Total instructors found: {}", instructors.size());
        return instructors;
    }

    @Override
    public Instructor insertOrUpdate(Instructor instructor, Long organizationId) {
        logger.info("Inserting/updating instructor: {}", instructor);
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    logger.error("Organization with ID {} not found", organizationId);
                    return new RuntimeException("Organization Not Found");
                });

        instructor.setOrganization(organization);
        Instructor savedInstructor = instructorRepository.save(instructor);
        logger.info("Instructor saved successfully with ID: {}", savedInstructor.getId());
        return savedInstructor;
    }

    @Override
    public boolean deleteInstructor(Long id) {
        logger.info("Deleting instructor with ID: {}", id);
        instructorRepository.deleteById(id);
        boolean exists = instructorRepository.findById(id).isPresent();
        if (!exists) {
            logger.info("Instructor with ID {} deleted successfully", id);
        } else {
            logger.warn("Instructor with ID {} still exists after deletion", id);
        }
        return !exists;
    }

    @Override
    public boolean deleteAllInstructors() {
        logger.info("Deleting all instructors");
        instructorRepository.deleteAll();
        boolean isEmpty = instructorRepository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All instructors deleted successfully");
        } else {
            logger.warn("Some instructors still exist after deletion");
        }
        return isEmpty;
    }

    public boolean enrollToCourse(Long instructorId, Long courseId) {
        logger.info("Enrolling instructor with ID {} to course ID {}", instructorId, courseId);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor with ID {} not found", instructorId);
                    return new RuntimeException("Instructor not found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course with ID {} not found", courseId);
                    return new RuntimeException("Course not found");
                });

        if (course.getInstructor() != null) {
            logger.warn("Course ID {} already has an instructor assigned", courseId);
            throw new RuntimeException("Instructor already enrolled to another course");
        }
        if (Objects.equals(instructor.getCourse().getId(), courseId)) {
            logger.warn("Instructor ID {} is already enrolled in course ID {}", instructorId, courseId);
            return false;
        }

        instructor.setCourse(course);
        instructorRepository.save(instructor);
        logger.info("Instructor with ID {} successfully enrolled to course ID {}", instructorId, courseId);

        return true;
    }

    public boolean withdrawFromCourse(Long instructorId, Long courseId) {
        logger.info("Withdrawing instructor with ID {} from course ID {}", instructorId, courseId);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor with ID {} not found", instructorId);
                    return new RuntimeException("Instructor not found");
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course with ID {} not found", courseId);
                    return new RuntimeException("Course not found");
                });

        if (!Objects.equals(instructor.getCourse().getId(), courseId)) {
            logger.warn("Instructor ID {} is not enrolled in course ID {}", instructorId, courseId);
            return false;
        }

        if (instructor.getCourse().equals(course)) {
            instructor.setCourse(null);
            course.setInstructor(null);
            logger.info("Instructor with ID {} successfully withdrawn from course ID {}", instructorId, courseId);
        }

        instructorRepository.save(instructor);
        courseRepository.save(course);

        return true;
    }
}