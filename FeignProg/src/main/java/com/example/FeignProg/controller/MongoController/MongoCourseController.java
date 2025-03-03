package com.example.FeignProg.controller.MongoController;

import com.example.FeignProg.dto.MongoDTO.CourseDTO;
import com.example.FeignProg.dto.MongoDTO.CourseReqDTO;
import com.example.FeignProg.service.MongoService.CourseService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/postFeignCourse")
public class MongoCourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/saveCourse")
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveCourse(@RequestBody CourseReqDTO courseDTO) {
        return ResponseEntity.ok(Objects.requireNonNull(courseService.saveMongoCourse(courseDTO).getBody()));
    }
    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> getCourseById(@RequestParam String id) {
        return courseService.getMongoCourse(id);
    }
}
