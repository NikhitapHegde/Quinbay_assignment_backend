package com.example.FeignProg.controller.PostgreController;

import com.example.FeignProg.dto.PostgreDTO.studentDTO;
import com.example.FeignProg.service.PostgreService.studentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postFeignStudent")
public class PostGreStudentController {

    @Autowired
    private studentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<studentDTO>> saveStudent(@RequestBody studentDTO studentDTO) {
        return studentService.savePostStudent(studentDTO);
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<studentDTO>> getStud(@RequestParam Long id) {
        return studentService.findPostOne(id);
    }

}
