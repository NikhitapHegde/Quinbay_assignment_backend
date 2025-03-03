package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.PostgreDTO.studentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface studentService {
    public ResponseEntity<ApiResponse<studentDTO>> savePostStudent(studentDTO studentDTO);
    public ResponseEntity<ApiResponse<studentDTO>> findPostOne(Long studentId);

}
