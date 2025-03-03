package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.feign.MongoFeign.InstructorInterface;
import com.example.FeignProg.service.MongoService.InstructorService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService {
    @Autowired
    private InstructorInterface InstructorInterface;
    public ResponseEntity<ApiResponse<InstructorDTO>> saveMongoInstructor(InstructorDTO instructorDTO) {
        return InstructorInterface.saveInstructor(instructorDTO);
    }
    public ResponseEntity<ApiResponse<InstructorDTO>> getMongoInstructor(String orgId) {
        return InstructorInterface.getById(orgId);
    }
    }
