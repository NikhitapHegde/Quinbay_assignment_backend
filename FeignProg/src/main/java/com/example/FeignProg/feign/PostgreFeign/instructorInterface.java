package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Postgre-Instructor", url = "http://localhost:8084/api/instructor")
public interface instructorInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<instructorDTO>> saveInstructor(@RequestBody instructorDTO instructorDTO);

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<instructorDTO>> getById(@RequestParam Long id);

    }
