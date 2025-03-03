package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentRpository;
import com.example.StudentManagementSystem.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StudentRpository studentRepository;

    @Override
    public Course getOne(String id) {
        logger.info("Fetching course with id: {}", id);
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepository.findAll();
    }

    @Override
    public Course insertOrUpdate(Course course, String instructorId, String organizationId) {
        logger.info("Inserting/Updating course: {}, Instructor ID: {}, Organization ID: {}", course, instructorId, organizationId);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor not found with ID: {}", instructorId);
                    return new RuntimeException("Instructor Not Found");
                });

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    logger.error("Organization not found with ID: {}", organizationId);
                    return new RuntimeException("Organization Not Found");
                });

        course.setInstructor(instructor);
        course.setOrganization(organization);

        Course savedCourse = courseRepository.save(course);
        logger.info("Course saved successfully with ID: {}", savedCourse.getId());
        return savedCourse;
    }

    @Override
    public boolean deleteCourse(String id) {
        logger.info("Deleting course with ID: {}", id);

        if (!courseRepository.existsById(id)) {
            logger.warn("Course with ID {} does not exist", id);
            return false;
        }

        courseRepository.deleteById(id);
        boolean deleted = !courseRepository.existsById(id);

        if (deleted) {
            logger.info("Course with ID {} deleted successfully", id);
        } else {
            logger.error("Failed to delete course with ID {}", id);
        }

        return deleted;
    }

    @Override
    public boolean deleteAllCourses() {
        logger.info("Deleting all courses");
        courseRepository.deleteAll();

        boolean isEmpty = courseRepository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All courses deleted successfully");
        } else {
            logger.error("Failed to delete all courses");
        }

        return isEmpty;
    }

    @Override
    public List<CourseDTO> getCourseDetails(String courseId) {
        logger.info("Fetching course details for ID: {}", courseId);

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            logger.warn("Course not found with ID: {}", courseId);
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setFee(course.getFee());

        InstructorDTO instructorDTO = new InstructorDTO();
        if (course.getInstructor() != null) {
            instructorDTO.setId(course.getInstructor().getId());
            instructorDTO.setName(course.getInstructor().getName());
            instructorDTO.setDateOfBirth(course.getInstructor().getDateOfBirth());
        }

        if (course.getInstructor().getOrganization() != null) {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(course.getOrganization().getId());
            organizationDTO.setName(course.getOrganization().getName());

            instructorDTO.setOrganizationDTO(organizationDTO);
        }

        OrganizationDTO organizationDTO = new OrganizationDTO();
        if (course.getOrganization() != null) {
            organizationDTO.setId(course.getOrganization().getId());
            organizationDTO.setName(course.getOrganization().getName());
        }

        courseDTO.setInstructorDTO(instructorDTO);
        courseDTO.setOrganizationDTO(organizationDTO);

        logger.info("Course details fetched successfully for ID: {}", courseId);

        return Collections.singletonList(courseDTO);
    }
}