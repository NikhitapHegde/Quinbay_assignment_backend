package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.*;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.entity.StudentCourse;
import com.example.StudentManagementSystem.services.StudentService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Received request to save/update student: {}", studentDTO);
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student = studentService.insertOrUpdate(student, studentDTO.getCourseIds(), studentDTO.getOrganizationId());
        BeanUtils.copyProperties(student, studentDTO);
        log.info("Student saved successfully: {}", studentDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student Saved Successfully", studentDTO));
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(@RequestParam String id) {
        log.info("Fetching student with ID: {}", id);
        Student student = studentService.getOne(id);
        if (student == null) {
            log.error("Student not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Not Found", "error"));
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        if (student.getOrganization() == null){
            log.error("Organization not found with id");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Not Found organization", "error"));
        }
        studentDTO.setOrganizationId(student.getOrganization().getId());
        studentDTO.setName(student.getName());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        List<String> courseIds = new ArrayList<>();
        if (student.getStudentCourse() != null){
            student.getStudentCourse().forEach(studentCourse ->
                    courseIds.add(studentCourse.getCourse().getId())
            );
        }
        studentDTO.setCourseIds(courseIds);

        log.info("Student found: {}", studentDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student is Present", studentDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteStud(@RequestParam String id) {
        log.info("Deleting student with ID: {}", id);
        boolean flag = studentService.deleteStudent(id);
        if (flag) {
            log.info("Student successfully deleted with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Deleted", flag));
        }
        log.error("Student not deleted with ID: {}", id);
        return ResponseEntity.ok(new ApiResponse<>("Student Not Deleted", "error"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam String studentId, @RequestParam String courseId) {
        log.info("Enrolling student with ID: {} to course with ID: {}", studentId, courseId);
        boolean flag = studentService.enrollToCourse(studentId, courseId);
        if (flag) {
            log.info("Student successfully enrolled to course with ID: {}", courseId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Enrolled to Course", flag));
        }
        log.error("Student not enrolled to course with ID: {}", courseId);
        return ResponseEntity.ok(new ApiResponse<>("Student Not Enrolled", "error"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String studentId, @RequestParam String courseId) {
        log.info("Withdrawing student with ID: {} from course with ID: {}", studentId, courseId);
        boolean flag = studentService.withdrawFromCourse(studentId, courseId);
        if (flag) {
            log.info("Student successfully withdrawn from course with ID: {}", courseId);
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Withdrawn from Course", flag));
        }
        log.error("Student not withdrawn from course with ID: {}", courseId);
        return ResponseEntity.ok(new ApiResponse<>("Student Not Withdrawn", "error"));
    }

    @GetMapping("/{studentStatus}/courses/progress")
    public ResponseEntity<List<StudentCourseProgressDTO>> getStudentByStatus(@PathVariable String studentStatus){
        log.info("Fetching students with progress for student with status: {}", studentStatus);
        List<StudentCourse> studentCourses = studentService.getStudentByProgress(studentStatus);
        List<StudentCourseProgressDTO> courseProgressList = studentCourses.stream().map(course -> {
            StudentCourseProgressDTO dto = new StudentCourseProgressDTO();
            BeanUtils.copyProperties(course, dto);
            return dto;
        }).collect(Collectors.toList());

        log.info("Fetched {} students with progress status: {}", courseProgressList.size(), studentStatus);
        return ResponseEntity.ok(courseProgressList);
    }
}