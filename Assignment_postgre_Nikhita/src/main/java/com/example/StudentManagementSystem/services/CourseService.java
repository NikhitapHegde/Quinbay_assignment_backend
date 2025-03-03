package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Course getOne(Long id);
    List<Course> getAllCourses();
    Course insertOrUpdate(Course course, Long instructorId, Long organizationId);
    boolean deleteCourse(Long id);
    boolean deleteAllCourses();
    List<CourseDTO> getCourseDetails(Long id);
}
