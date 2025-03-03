package com.example.FeignProg.feign.PostgreFeign;


import com.example.FeignProg.dto.PostgreDTO.studentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Postgre-Student", url = "http://localhost:8084/api/student")
public interface studentInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<studentDTO>> saveStudent(@RequestBody studentDTO studentDTO);

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<studentDTO>> getStud(@RequestParam Long id);

}
