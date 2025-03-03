package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.services.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Organization getOne(String id) {
        logger.info("Fetching organization with ID: {}", id);
        return organizationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        logger.info("Fetching all organizations");
        return organizationRepository.findAll();
    }

    @Override
    public Organization insertOrUpdate(Organization organization) {
        logger.info("Attempting to save organization: {}", organization);

        Organization savedOrg = organizationRepository.save(organization);
        if (savedOrg == null || savedOrg.getId() == null) {
            logger.error("Organization save failed");
        } else {
            logger.info("Organization saved successfully in database: {}", mongoTemplate.getDb().getName());
        }
        return savedOrg;
    }

    @Override
    public boolean deleteOrganization(String id) {
        logger.info("Checking existence of organization with ID: {}", id);
        boolean exists = organizationRepository.existsById(id);
        if (exists) {
            logger.info("Organization with ID {} exists", id);
        } else {
            logger.warn("Organization with ID {} does not exist", id);
        }
        return exists;
    }

    @Override
    public boolean deleteAllOrganizations() {
        logger.info("Deleting all organizations");
        organizationRepository.deleteAll();
        boolean isEmpty = organizationRepository.findAll().isEmpty();

        if (isEmpty) {
            logger.info("All organizations deleted successfully");
        } else {
            logger.error("Failed to delete all organizations");
        }

        return isEmpty;
    }

    @Override
    public Long getCountOfStudents(String id) {
        logger.info("Fetching student count for organization ID: {}", id);
        return organizationRepository.countById(id);
    }

    public Long getCountOfInstructors(String id) {
        logger.info("Fetching instructor count for organization ID: {}", id);
        return organizationRepository.countById(id);
    }
}