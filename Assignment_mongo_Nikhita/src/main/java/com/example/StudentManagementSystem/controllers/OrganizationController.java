package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.services.OrganizationService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private CourseService courseService;



    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO) {
        log.info("Received request to save/update organization: {}", organizationDTO);
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        organization = organizationService.insertOrUpdate(organization);
        BeanUtils.copyProperties(organization, organizationDTO);
        log.info("Organization saved successfully: {}", organizationDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Organization Created Successfully", organizationDTO));
    }

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam String id) {
        log.info("Fetching organization with ID: {}", id);
        Organization organization = organizationService.getOne(id);
        if (organization == null) {
            log.error("Organization not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Organization Not Found", "error"));
        }

        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(organization, organizationDTO);
        log.info("Organization found: {}", organizationDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Organization Found", organizationDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrg(@RequestParam String id) {
        log.info("Deleting organization with ID: {}", id);
        Boolean flag = organizationService.deleteOrganization(id);
        if (flag) {
            log.info("Organization successfully deleted with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Organization Successfully Deleted", flag));
        }
        log.error("Organization not deleted with ID: {}", id);
        return ResponseEntity.ok(new ApiResponse<>("error", "Organization Not Deleted", flag));
    }

    @GetMapping("/instructor/count")
    public ResponseEntity<ApiResponse<Long>> countInstructors(@RequestParam String orgId) {
        log.info("Fetching total instructor count for organization ID: {}", orgId);
        Long instructorCount = organizationService.getCountOfInstructors(orgId);
        log.info("Total instructor count for organization ID {}: {}", orgId, instructorCount);
        return ResponseEntity.ok(new ApiResponse<>("success", "Total instructors count", instructorCount));
    }

    @GetMapping("/courses/details")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getCourseDetails(@RequestParam String id) {
        log.info("Fetching course details for organization ID: {}", id);
        List<CourseDTO> courseDTOs = courseService.getCourseDetails(id);
        log.info("Fetched course details for organization ID {}: {}", id, courseDTOs.size());
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched course details", courseDTOs));
    }
}