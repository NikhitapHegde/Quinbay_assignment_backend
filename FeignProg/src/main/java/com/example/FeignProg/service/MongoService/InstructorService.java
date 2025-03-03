package com.example.FeignProg.service.MongoService;

import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InstructorService {
    public ResponseEntity<ApiResponse<InstructorDTO>> saveMongoInstructor(InstructorDTO instructorDTO);
    public ResponseEntity<ApiResponse<InstructorDTO>> getMongoInstructor(String id);
}
