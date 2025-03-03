package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Mongo-Student", url = "http://localhost:8092/api/student")
public interface StudentInterface {

    @PostMapping("/saveOrUpdate")
    ResponseEntity<ApiResponse<StudentDTO>> saveStudent(StudentDTO studentDTO);

    @GetMapping("/getStudent")
    ResponseEntity<ApiResponse<StudentDTO>> getStud(String id);

}
