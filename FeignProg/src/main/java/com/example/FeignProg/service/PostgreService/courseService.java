package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.PostgreDTO.courseDTO;
import com.example.FeignProg.dto.PostgreDTO.courseReqDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface courseService {
    public ResponseEntity<ApiResponse<courseReqDTO>> savePostCourse(courseReqDTO courseDTO);
    public ResponseEntity<ApiResponse<courseDTO>> getPostCourse(Long courseId);
}
