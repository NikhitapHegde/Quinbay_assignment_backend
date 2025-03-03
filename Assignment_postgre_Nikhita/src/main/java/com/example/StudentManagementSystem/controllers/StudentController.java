package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.*;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.services.StudentService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO) {
        try {
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
            student = studentService.insertOrUpdate(student, studentDTO.getCourseIds(), studentDTO.getOrganizationId());
            BeanUtils.copyProperties(student, studentDTO);
            logger.info("Student saved/updated successfully: {}", studentDTO);
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Saved Successfully", studentDTO));
        } catch (Exception e) {
            logger.error("Error saving/updating student: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error saving/updating student", null));
        }
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(@RequestParam Long id) {
        try {
            Student student = studentService.getOne(id);
            if (student == null) {
                logger.warn("Student not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Not Found", "error"));
            }

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setOrganizationId(student.getOrganization().getId());
            studentDTO.setName(student.getName());
            studentDTO.setDateOfBirth(student.getDateOfBirth());

            List<Long> courseIds = new ArrayList<>();
            student.getStudentCourse().forEach(studentCourse -> courseIds.add(studentCourse.getCourse().getId()));
            studentDTO.setCourseIds(courseIds);

            logger.info("Student found with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Student is Present", studentDTO));
        } catch (Exception e) {
            logger.error("Error fetching student with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error fetching student", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteStud(@RequestParam Long id) {
        try {
            boolean flag = studentService.deleteStudent(id);
            if (flag) {
                logger.info("Student deleted successfully with ID: {}", id);
                return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Deleted", flag));
            }
            logger.warn("Failed to delete student with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Student Not Deleted", "error"));
        } catch (Exception e) {
            logger.error("Error deleting student with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error deleting student", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        try {
            boolean flag = studentService.enrollToCourse(studentId, courseId);
            if (flag) {
                logger.info("Student with ID: {} successfully enrolled in course with ID: {}", studentId, courseId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Enrolled to Course", flag));
            }
            logger.warn("Student with ID: {} not enrolled in course with ID: {}", studentId, courseId);
            return ResponseEntity.ok(new ApiResponse<>("Student Not enrolled", "error"));
        } catch (Exception e) {
            logger.error("Error enrolling student with ID: {} to course with ID: {}", studentId, courseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error enrolling student", null));
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        try {
            boolean flag = studentService.withdrawFromCourse(studentId, courseId);
            if (flag) {
                logger.info("Student with ID: {} successfully withdrawn from course with ID: {}", studentId, courseId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Withdrawn from Course", flag));
            }
            logger.warn("Student with ID: {} not withdrawn from course with ID: {}", studentId, courseId);
            return ResponseEntity.ok(new ApiResponse<>("Student Not withdrawn", "error"));
        } catch (Exception e) {
            logger.error("Error withdrawing student with ID: {} from course with ID: {}", studentId, courseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Error withdrawing student", null));
        }
    }
}
