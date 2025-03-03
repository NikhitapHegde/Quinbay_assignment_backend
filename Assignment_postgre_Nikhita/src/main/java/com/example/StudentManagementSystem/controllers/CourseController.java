package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.CourseReqDTO;
import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    /*@PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveCourse(@RequestBody CourseReqDTO courseDTO) {
        logger.info("Request received to save or update course: {}", courseDTO);

        try {
            Course course = new Course();
            BeanUtils.copyProperties(courseDTO, course);
            course = courseService.insertOrUpdate(course, courseDTO.getInstructorId(), courseDTO.getOrganizationID());
            BeanUtils.copyProperties(course, courseDTO);

            logger.info("Course saved or updated successfully: {}", courseDTO);
            return ResponseEntity.ok(new ApiResponse<>("success", "Course saved Successfully", courseDTO));
        } catch (Exception e) {
            logger.error("Error while saving or updating course: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Error saving or updating course"));
        }
    }*/
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseReqDTO>> saveCourse(@RequestBody CourseReqDTO courseDTO ) {
        logger.info("Received request to save/update course: {}", courseDTO);
        if (courseDTO.getInstructorID() == null || courseDTO.getOrganizationID() == null){
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "id cannot be null",null));
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        Course savedCourse = courseService.insertOrUpdate(course, courseDTO.getInstructorID(), courseDTO.getOrganizationID());

        CourseReqDTO savedCourseDTO = new CourseReqDTO();
        BeanUtils.copyProperties(savedCourse, savedCourseDTO);
        logger.info("Course saved successfully: {}", savedCourseDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course saved successfully", savedCourseDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll() {
        logger.info("Request received to fetch all courses");

        try {
            List<Course> courses = courseService.getAllCourses();
            List<CourseDTO> courseDTOS = courses.stream().map((course) -> {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setFee(course.getFee());
                InstructorDTO instructorDTO = new InstructorDTO();
                instructorDTO.setId(course.getInstructor().getId());
                instructorDTO.setName(course.getInstructor().getName());
                instructorDTO.setDateOfBirth(course.getInstructor().getDateOfBirth());
                OrganizationDTO organizationDTO = new OrganizationDTO();
                organizationDTO.setId(course.getOrganization().getId());
                organizationDTO.setName(course.getOrganization().getName());
                instructorDTO.setOrganizationDTO(organizationDTO);
                courseDTO.setInstructorDTO(instructorDTO);
                courseDTO.setId(course.getId());
                courseDTO.setName(course.getName());
                courseDTO.setOrganizationDTO(organizationDTO);
                return courseDTO;
            }).collect(Collectors.toList());

            logger.info("Fetched all courses successfully");
            return ResponseEntity.ok(new ApiResponse<>("success", "Fetched All Courses", courseDTOS));
        } catch (Exception e) {
            logger.error("Error fetching courses: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Error fetching courses"));
        }
    }

    /*@GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam Long id) {
        logger.info("Request received to fetch course by id: {}", id);

        try {
            Course course = courseService.getOne(id);
            if (course == null) {
                logger.warn("Course not found for id: {}", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<CourseDTO>("Not Found", "error"));
            }

            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setFee(course.getFee());
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setId(course.getInstructor().getId());
            instructorDTO.setName(course.getInstructor().getName());
            instructorDTO.setDateOfBirth(course.getInstructor().getDateOfBirth());
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(course.getOrganization().getId());
            organizationDTO.setName(course.getOrganization().getName());
            instructorDTO.setOrganizationDTO(organizationDTO);
            courseDTO.setInstructorDTO(instructorDTO);
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setOrganizationDTO(organizationDTO);

            logger.info("Course details fetched successfully for id: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Course is Present", courseDTO));
        } catch (Exception e) {
            logger.error("Error fetching course by id: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Error fetching course"));
        }
    }*/
    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam Long id) {
        logger.info("Fetching course with ID: {}", id);
        Course course = courseService.getOne(id);
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        logger.info("Course found: {}", courseDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course found", courseDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam Long id) {
        logger.info("Request received to delete course with id: {}", id);

        try {
            Boolean flag = courseService.deleteCourse(id);
            if (flag) {
                logger.info("Course successfully deleted with id: {}", id);
                return ResponseEntity.ok(new ApiResponse<>("success", "Course Successfully Deleted", flag));
            }

            logger.warn("Course deletion failed for id: {}", id);
            return ResponseEntity.ok(new ApiResponse<>("Course Not Deleted", "error"));
        } catch (Exception e) {
            logger.error("Error deleting course: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Error deleting course"));
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse<Boolean>> deleteAllCourses() {
        logger.info("Request received to delete all courses");

        try {
            Boolean flag = courseService.deleteAllCourses();
            if (flag) {
                logger.info("All courses successfully deleted");
                return ResponseEntity.ok(new ApiResponse<>("success", "Courses Successfully Deleted", flag));
            }

            logger.warn("All courses deletion failed");
            return ResponseEntity.ok(new ApiResponse<>("Courses Not Deleted", "error"));
        } catch (Exception e) {
            logger.error("Error deleting all courses: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Error deleting all courses"));
        }
    }
}
