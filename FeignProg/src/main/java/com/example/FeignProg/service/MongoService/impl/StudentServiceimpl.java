package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.feign.MongoFeign.StudentInterface;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceimpl implements StudentService {

    @Autowired
    private StudentInterface feignMongoInterface;

    @Override
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(String id) {
        return feignMongoInterface.getStud(id);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(StudentDTO studentDTO) {
        return feignMongoInterface.saveStudent(studentDTO);
    }

}
