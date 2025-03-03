package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Organization getOne(Long id) {
        logger.info("Fetching organization with ID: {}", id);
        Organization organization = organizationRepository.findById(id).orElse(null);
        if (organization == null) {
            logger.warn("Organization with ID {} not found", id);
        } else {
            logger.debug("Organization details: {}", organization);
        }
        return organization;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        logger.info("Fetching all organizations");
        List<Organization> organizations = organizationRepository.findAll();
        logger.debug("Total organizations found: {}", organizations.size());
        return organizations;
    }

    @Override
    public Organization insertOrUpdate(Organization organization) {
        logger.info("Inserting/updating organization: {}", organization);
        Organization savedOrganization = entityManager.merge(organization);
        logger.info("Organization saved successfully with ID: {}", savedOrganization.getId());
        return savedOrganization;
    }

    @Override
    public boolean deleteOrganization(Long id) {
        logger.info("Deleting organization with ID: {}", id);
        organizationRepository.deleteById(id);
        boolean exists = organizationRepository.findById(id).isPresent();
        if (!exists) {
            logger.info("Organization with ID {} deleted successfully", id);
        } else {
            logger.warn("Organization with ID {} still exists after deletion", id);
        }
        return !exists;
    }

    @Override
    public boolean deleteAllOrganizations() {
        logger.info("Deleting all organizations");
        organizationRepository.deleteAll();
        boolean isEmpty = organizationRepository.findAll().isEmpty();
        if (isEmpty) {
            logger.info("All organizations deleted successfully");
        } else {
            logger.warn("Some organizations still exist after deletion");
        }
        return isEmpty;
    }

    @Override
    public Long getCountOfStudents(Long id) {
        logger.info("Counting students in organization with ID: {}", id);
        Long count = organizationRepository.countStudentsByOrgId(id);
        logger.debug("Total students found: {}", count);
        return count;
    }

    public Long getCountOfInstructors(Long id) {
        logger.info("Counting instructors in organization with ID: {}", id);
        Long count = organizationRepository.countInstructorsByOrgId(id);
        logger.debug("Total instructors found: {}", count);
        return count;
    }

    public Long getCountOfStudentsByCourse(Long id) {
        logger.info("Counting students enrolled in course with ID: {}", id);
        Long count = organizationRepository.countStudentsByCourseId(id);
        logger.debug("Total students found in course ID {}: {}", id, count);
        return count;
    }

    public StudentDTO getStudentsByCourseId(Long id) {
        logger.info("Fetching students for course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Course with ID {} not found", id);
                    return new RuntimeException("Course not found");
                });

        Instructor instructor = instructorRepository.findById(course.getId())
                .orElseThrow(() -> {
                    logger.error("Instructor for course ID {} not found", id);
                    return new RuntimeException("Instructor not found");
                });

        logger.debug("Returning student data for course ID {}", id);
        return null;
    }
}