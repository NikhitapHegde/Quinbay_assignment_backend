package com.example.FeignProg.controller.MongoController;

import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/postFeignStudent")
public class MongoStudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = "/student/getById")
    public ResponseEntity<ApiResponse<StudentDTO>> getById(String id) {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.getStud(id).getBody()));
    }

    @PostMapping(value = "/student/saveStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> saveTheStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.saveStudent(studentDTO).getBody()));
    }
}