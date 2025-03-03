package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Mongo-Instructor", url = "http://localhost:8092/api/instructor")
public interface InstructorInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO);

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam String id);
}
