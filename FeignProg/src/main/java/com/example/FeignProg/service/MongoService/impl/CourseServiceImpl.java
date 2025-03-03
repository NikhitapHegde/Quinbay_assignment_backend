package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.CourseDTO;
import com.example.FeignProg.dto.MongoDTO.CourseReqDTO;
import com.example.FeignProg.feign.MongoFeign.CourseInterface;
import com.example.FeignProg.service.MongoService.CourseService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseInterface courseInterface;

    public ResponseEntity<ApiResponse<CourseReqDTO>> saveMongoCourse(CourseReqDTO courseDTO) {
        if (courseDTO.getInstructorID() == null || courseDTO.getOrganizationID() == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "id cannot be null", null));
        }
            return courseInterface.saveCourse(courseDTO);

    }

    public ResponseEntity<ApiResponse<CourseDTO>> getMongoCourse(String orgId) {
        return courseInterface.getById(orgId);
    }
}

