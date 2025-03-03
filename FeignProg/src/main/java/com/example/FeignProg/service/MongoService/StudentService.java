package com.example.FeignProg.service.MongoService;

import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public interface StudentService {

    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(StudentDTO studentDTO);

    public ResponseEntity<ApiResponse<StudentDTO>> getStud(String id);

}
