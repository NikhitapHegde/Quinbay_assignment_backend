package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.CourseReqDTO;
import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveCourse(@RequestBody CourseReqDTO courseDTO ) {
        log.info("Received request to save/update course: {}", courseDTO);
        if (courseDTO.getInstructorID() == null || courseDTO.getOrganizationID() == null){
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "id cannot be null",null));
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        Course savedCourse = courseService.insertOrUpdate(course, courseDTO.getInstructorID(), courseDTO.getOrganizationID());

        CourseReqDTO savedCourseDTO = new CourseReqDTO();
        BeanUtils.copyProperties(savedCourse, savedCourseDTO);
        log.info("Course saved successfully: {}", savedCourseDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course saved successfully", savedCourseDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll() {
        log.info("Fetching all courses");
        List<Course> courses = courseService.getAllCourses();
        List<CourseDTO> courseDTOS = courses.stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            BeanUtils.copyProperties(course, courseDTO);
            return courseDTO;
        }).collect(Collectors.toList());
        log.info("Fetched {} courses", courseDTOS.size());
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched all courses", courseDTOS));
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam String id) {
        log.info("Fetching course with ID: {}", id);
        Course course = courseService.getOne(id);
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        log.info("Course found: {}", courseDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course found", courseDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam String id) {
        log.info("Deleting course with ID: {}", id);
        Boolean isDeleted = courseService.deleteCourse(id);
        log.info("Course deletion status: {}", isDeleted);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course deleted successfully", isDeleted));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<Boolean>> deleteAllCourses() {
        log.info("Deleting all courses");
        Boolean isDeleted = courseService.deleteAllCourses();
        log.info("All courses deleted: {}", isDeleted);
        return ResponseEntity.ok(new ApiResponse<>("success", "All courses deleted successfully", isDeleted));
    }
}