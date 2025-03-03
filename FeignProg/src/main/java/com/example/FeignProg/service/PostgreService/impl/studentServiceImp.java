package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.PostgreDTO.studentDTO;
import com.example.FeignProg.service.PostgreService.studentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Primary
@Service
public class studentServiceImp implements studentService {

    @Autowired
    private com.example.FeignProg.feign.PostgreFeign.studentInterface studentInterface;


    @Override
    public ResponseEntity<ApiResponse<studentDTO>> savePostStudent(studentDTO studentDTO) {
        return studentInterface.saveStudent(studentDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<studentDTO>> findPostOne(Long studentId) {
        return studentInterface.getStud(studentId);
    }
}
