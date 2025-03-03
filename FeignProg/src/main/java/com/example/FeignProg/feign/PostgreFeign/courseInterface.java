package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.PostgreDTO.courseDTO;
import com.example.FeignProg.dto.PostgreDTO.courseReqDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Postgre-Course", url = "http://localhost:8084/api/course")
public interface courseInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<courseReqDTO>> saveCourse(@RequestBody courseReqDTO courseDTO);

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<courseDTO>> get(@RequestParam Long id);
}
