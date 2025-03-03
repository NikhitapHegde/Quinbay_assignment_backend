package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.services.InstructorService;
import com.example.StudentManagementSystem.services.StudentService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        try {
            logger.info("Saving or updating instructor with name: {}", instructorDTO.getName());
            Instructor instructor = new Instructor();
            BeanUtils.copyProperties(instructorDTO, instructor);
            instructor = instructorService.insertOrUpdate(instructor, instructorDTO.getOrganizationDTO().getId());
            BeanUtils.copyProperties(instructor, instructorDTO);
            logger.info("Instructor saved successfully with id: {}", instructorDTO.getId());
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Saved Successfully", instructorDTO));
        } catch (Exception e) {
            logger.error("Error while saving/updating instructor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", "Failed to save instructor", null));
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam Long id) {
        try {
            logger.info("Fetching instructor details for id: {}", id);
            Instructor instructor = instructorService.getOne(id);
            if (instructor == null) {
                logger.error("Instructor not found for id: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Instructor Not Found", "error"));
            }
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setId(instructor.getId());
            instructorDTO.setName(instructor.getName());
            instructorDTO.setDateOfBirth(instructor.getDateOfBirth());
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(instructor.getOrganization().getId());
            organizationDTO.setName(instructor.getOrganization().getName());
            instructorDTO.setOrganizationDTO(organizationDTO);

            logger.info("Instructor details fetched successfully for id: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Found", instructorDTO));
        } catch (Exception e) {
            logger.error("Error fetching instructor by id: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", "Failed to fetch instructor", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam Long id) {
        try {
            logger.info("Deleting instructor with id: {}", id);
            Boolean flag = instructorService.deleteInstructor(id);
            if (flag) {
                logger.info("Instructor successfully deleted with id: {}", id);
                return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Deleted", flag));
            }
            logger.error("Failed to delete instructor with id: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Instructor Not Deleted", "error"));
        } catch (Exception e) {
            logger.error("Error deleting instructor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", "Failed to delete instructor", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam Long instructorId, @RequestParam Long courseId) {
        try {
            logger.info("Registering instructor with id: {} to course with id: {}", instructorId, courseId);
            boolean flag = instructorService.enrollToCourse(instructorId, courseId);
            if (flag) {
                logger.info("Instructor with id: {} successfully enrolled to course with id: {}", instructorId, courseId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Enrolled to Course", flag));
            }
            logger.error("Instructor with id: {} failed to enroll to course with id: {}", instructorId, courseId);
            return ResponseEntity.ok(new ApiResponse<>("Instructor Not Enrolled", "error"));
        } catch (Exception e) {
            logger.error("Error enrolling instructor to course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", "Failed to enroll instructor", null));
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam Long instructorId, @RequestParam Long courseId) {
        try {
            logger.info("Withdrawing instructor with id: {} from course with id: {}", instructorId, courseId);
            boolean flag = instructorService.withdrawFromCourse(instructorId, courseId);
            if (flag) {
                logger.info("Instructor with id: {} successfully withdrawn from course with id: {}", instructorId, courseId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Withdrawn from Course", flag));
            }
            logger.error("Instructor with id: {} failed to withdraw from course with id: {}", instructorId, courseId);
            return ResponseEntity.ok(new ApiResponse<>("Instructor Not Withdrawn", "error"));
        } catch (Exception e) {
            logger.error("Error withdrawing instructor from course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", "Failed to withdraw instructor", null));
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateCourseStatus(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam String status) {
        try {
            logger.info("Updating course status for student id: {} and course id: {}", studentId, courseId);
            return studentService.updateCourseStatus(studentId, courseId, status)
                    ? ResponseEntity.ok("Course status updated")
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update course status");
        } catch (Exception e) {
            logger.error("Error updating course status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating course status");
        }
    }
}
