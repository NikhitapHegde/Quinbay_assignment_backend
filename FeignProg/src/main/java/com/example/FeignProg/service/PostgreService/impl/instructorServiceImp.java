package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.service.PostgreService.instructorService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class instructorServiceImp implements instructorService {

    @Autowired
    public com.example.FeignProg.feign.PostgreFeign.instructorInterface instructorInterface;


    @Override
    public ResponseEntity<ApiResponse<instructorDTO>> savePostInstructor(instructorDTO instructorDTO) {
        return instructorInterface.saveInstructor(instructorDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<instructorDTO>> getPostInstructor(Long instructorId) {
        return instructorInterface.getById(instructorId);
    }
}
