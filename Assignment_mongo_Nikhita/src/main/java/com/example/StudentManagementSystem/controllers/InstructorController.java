package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.services.InstructorService;
import com.example.StudentManagementSystem.services.StudentService;
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
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        log.info("Received request to save/update instructor: {}", instructorDTO);
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDTO, instructor);
        instructor = instructorService.insertOrUpdate(instructor, instructorDTO.getOrganizationDTO().getId());
        BeanUtils.copyProperties(instructor, instructorDTO);
        log.info("Instructor saved successfully: {}", instructorDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Saved Successfully", instructorDTO));
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam String id) {
        log.info("Fetching instructor with ID: {}", id);
        Instructor instructor = instructorService.getOne(id);
        if (instructor == null) {
            log.error("Instructor not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Instructor Not Found", "error"));
        }
        InstructorDTO instructorDTO = new InstructorDTO();
        BeanUtils.copyProperties(instructor, instructorDTO);
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(instructor.getOrganization(), organizationDTO);
        instructorDTO.setOrganizationDTO(organizationDTO);
        log.info("Instructor found: {}", instructorDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Found", instructorDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam String id) {
        log.info("Deleting instructor with ID: {}", id);
        Boolean flag = instructorService.deleteInstructor(id);
        if (flag) {
            log.info("Instructor successfully deleted with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Deleted", flag));
        }
        log.error("Instructor not deleted with ID: {}", id);
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not Deleted", "error"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam String instructorId, @RequestParam String courseId) {
        log.info("Instructor with ID: {} enrolling to course with ID: {}", instructorId, courseId);
        boolean flag = instructorService.enrollToCourse(instructorId, courseId);
        if (flag) {
            log.info("Instructor successfully enrolled to course with ID: {}", courseId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Enrolled to Course", flag));
        }
        log.error("Instructor not enrolled to course with ID: {}", courseId);
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not enrolled", "error"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String instructorId, @RequestParam String courseId) {
        log.info("Instructor with ID: {} withdrawing from course with ID: {}", instructorId, courseId);
        boolean flag = instructorService.withdrawFromCourse(instructorId, courseId);
        if (flag) {
            log.info("Instructor successfully withdrawn from course with ID: {}", courseId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Withdrawn from Course", flag));
        }
        log.error("Instructor not withdrawn from course with ID: {}", courseId);
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not withdrawn", "error"));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<String> updateCourseStatus(@RequestParam String studentId, @RequestParam String courseId, @RequestParam String status) {
        log.info("Updating course status for student ID: {} in course ID: {} with status: {}", studentId, courseId, status);
        return studentService.updateCourseStatus(studentId, courseId, status)
                ? ResponseEntity.ok("Course status updated")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add course");
    }
}