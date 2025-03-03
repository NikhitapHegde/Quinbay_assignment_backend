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
    public Course getOne(Long id) {
        logger.info("Fetching course with ID: {}", id);
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            logger.warn("Course with ID {} not found", id);
        } else {
            logger.debug("Course details: {}", course);
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        List<Course> courses = courseRepository.findAll();
        logger.debug("Total courses found: {}", courses.size());
        return courses;
    }

    @Override
    public Course insertOrUpdate(Course course, Long instructorId, Long organizationId) {
        logger.info("Inserting/updating course: {}", course);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("Instructor with ID {} not found", instructorId);
                    return new RuntimeException("Instructor Not Found");
                });

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> {
                    logger.error("Organization with ID {} not found", organizationId);
                    return new RuntimeException("Organization Not Found");
                });

        course.setInstructor(instructor);
        course.setOrganization(organization);
        Course savedCourse = courseRepository.save(course);
        logger.info("Course saved successfully with ID: {}", savedCourse.getId());
        return savedCourse;
    }

    @Override
    public boolean deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);
        courseRepository.deleteById(id);
        boolean exists = courseRepository.findById(id).isPresent();
        if (!exists) {
            logger.info("Course with ID {} deleted successfully", id);
        } else {
            logger.warn("Course with ID {} still exists after deletion", id);
        }
        return !exists;
    }

    @Override
    public boolean deleteAllCourses() {
        logger.info("Deleting all courses");
        courseRepository.deleteAll();
        boolean isEmpty = courseRepository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All courses deleted successfully");
        } else {
            logger.warn("Some courses still exist after deletion");
        }
        return isEmpty;
    }

    @Override
    public List<CourseDTO> getCourseDetails(Long courseId) {
        logger.info("Fetching course details for course ID: {}", courseId);
        Course course = courseRepository.getCourseWithDetails(courseId);

        if (course == null) {
            logger.warn("Course with ID {} not found", courseId);
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setFee(course.getFee());

        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(course.getInstructor().getId());
        instructorDTO.setName(course.getInstructor().getName());
        instructorDTO.setDateOfBirth(course.getInstructor().getDateOfBirth());

        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(course.getOrganization().getId());
        organizationDTO.setName(course.getOrganization().getName());

        instructorDTO.setOrganizationDTO(organizationDTO);
        courseDTO.setInstructorDTO(instructorDTO);
        courseDTO.setOrganizationDTO(organizationDTO);

        List<CourseDTO> courseDTO1 = Collections.singletonList(courseDTO);
        logger.info("Course details fetched successfully for ID: {}", courseId);
        return courseDTO1;
    }
}