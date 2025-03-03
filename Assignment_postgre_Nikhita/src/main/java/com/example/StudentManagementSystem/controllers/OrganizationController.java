package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.services.OrganizationService;
import com.example.StudentManagementSystem.utils.ApiResponse;
//import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO) {
        try {
            Organization organization = new Organization();
            BeanUtils.copyProperties(organizationDTO, organization);
            organization = organizationService.insertOrUpdate(organization);
            BeanUtils.copyProperties(organization, organizationDTO);

            logger.info("Organization saved or updated successfully with id: {}", organizationDTO.getId());

            return ResponseEntity.ok(new ApiResponse<>("success", "Organization Created Successfully", organizationDTO));
        } catch (Exception e) {
            logger.error("Error occurred while saving/updating organization: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while saving/updating organization", null));
        }
    }

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam Long id) {
        try {
            Organization organization = organizationService.getOne(id);
            if (organization == null) {
                logger.warn("Organization with id {} not found", id);
                return ResponseEntity.badRequest().body(new ApiResponse<>("Organization Not Found", "error"));
            }

            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(organization.getId());
            organizationDTO.setName(organization.getName());

            return ResponseEntity.ok(new ApiResponse<>("success", "Organization Found", organizationDTO));
        } catch (Exception e) {
            logger.error("Error occurred while fetching organization with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while fetching organization", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrg(@RequestParam Long id) {
        try {
            Boolean flag = organizationService.deleteOrganization(id);
            if (flag) {
                logger.info("Organization with id {} successfully deleted", id);
                return ResponseEntity.ok(new ApiResponse<>("success", "Organization Successfully Deleted", flag));
            }
            logger.warn("Organization with id {} was not deleted", id);
            return ResponseEntity.ok(new ApiResponse<>("Organization Not Deleted", "error"));
        } catch (Exception e) {
            logger.error("Error occurred while deleting organization with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while deleting organization", null));
        }
    }

    @GetMapping("/student/count")
    public ResponseEntity<ApiResponse<Long>> countStudentsPerCourse(@RequestParam Long orgId) {
        try {
            Long studentCount = organizationService.getCountOfStudentsByCourse(orgId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Total students count", studentCount));
        } catch (Exception e) {
            logger.error("Error occurred while counting students for organization id {}: {}", orgId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while counting students", null));
        }
    }

    @GetMapping("/instructor/count")
    public ResponseEntity<ApiResponse<Long>> countInstructors(@RequestParam Long orgId) {
        try {
            Long courseCount = organizationService.getCountOfInstructors(orgId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Total instructors count", courseCount));
        } catch (Exception e) {
            logger.error("Error occurred while counting instructors for organization id {}: {}", orgId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while counting instructors", null));
        }
    }

    @GetMapping("/courses/details")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCourseDetails(@RequestParam Long id) {
        try {
            List<CourseDTO> courseDTO = courseService.getCourseDetails(id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Fetched course details successfully", courseDTO));
        } catch (Exception e) {
            logger.error("Error occurred while fetching course details for organization id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error occurred while fetching course details", null));
        }
    }
}




