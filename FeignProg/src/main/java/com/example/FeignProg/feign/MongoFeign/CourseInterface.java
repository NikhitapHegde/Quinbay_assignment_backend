package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.MongoDTO.CourseDTO;
import com.example.FeignProg.dto.MongoDTO.CourseReqDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Mongo-Course", url = "http://localhost:8092/api/course")
public interface CourseInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveCourse(@RequestBody CourseReqDTO courseDTO);

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> getById(@RequestParam String id);

}
