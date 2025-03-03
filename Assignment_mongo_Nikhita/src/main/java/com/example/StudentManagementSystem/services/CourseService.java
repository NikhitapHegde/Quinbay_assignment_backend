package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.CourseReqDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Course getOne(String id);
    List<Course> getAllCourses();
    Course insertOrUpdate(Course course, String instructorId, String organizationId);
    boolean deleteCourse(String id);
    boolean deleteAllCourses();
    List<CourseDTO> getCourseDetails(String id);
}
