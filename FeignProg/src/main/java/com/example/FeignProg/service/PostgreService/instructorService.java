package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface instructorService {
    public ResponseEntity<ApiResponse<instructorDTO>> savePostInstructor(instructorDTO instructorDTO);
    public ResponseEntity<ApiResponse<instructorDTO>> getPostInstructor(Long instructorId);
}
