package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.PostgreDTO.courseDTO;
import com.example.FeignProg.dto.PostgreDTO.courseReqDTO;
import com.example.FeignProg.service.PostgreService.courseService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class courseServiceImp implements courseService {

    @Autowired
    private com.example.FeignProg.feign.PostgreFeign.courseInterface courseInterface;

    @Override
    public ResponseEntity<ApiResponse<courseReqDTO>> savePostCourse(courseReqDTO courseDTO) {
        return courseInterface.saveCourse(courseDTO);
    }
    @Override
    public ResponseEntity<ApiResponse<courseDTO>> getPostCourse(Long courseId) {
        return courseInterface.get(courseId);
    }

}
