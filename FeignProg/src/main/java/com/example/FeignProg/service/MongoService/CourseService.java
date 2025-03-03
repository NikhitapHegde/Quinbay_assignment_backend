package com.example.FeignProg.service.MongoService;

import com.example.FeignProg.dto.MongoDTO.CourseDTO;
import com.example.FeignProg.dto.MongoDTO.CourseReqDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveMongoCourse(CourseReqDTO courseDTO);
    public ResponseEntity<ApiResponse<CourseDTO>> getMongoCourse(String id);

}
