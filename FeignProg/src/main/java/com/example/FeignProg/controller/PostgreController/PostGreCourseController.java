package com.example.FeignProg.controller.PostgreController;

import com.example.FeignProg.dto.PostgreDTO.courseReqDTO;
import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.FeignProg.service.PostgreService.courseService;
import com.example.FeignProg.dto.PostgreDTO.courseDTO;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postFeignCourse")
public class PostGreCourseController {

    @Autowired
    private courseService courseService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<courseReqDTO>> saveStudent(@RequestBody courseReqDTO courseDTO) {
        return courseService.savePostCourse(courseDTO);
    }
    @GetMapping("/getCourse")
    public ResponseEntity<ApiResponse<courseDTO>> getStud(@RequestParam Long id) {
        return courseService.getPostCourse(id);

    }
}
