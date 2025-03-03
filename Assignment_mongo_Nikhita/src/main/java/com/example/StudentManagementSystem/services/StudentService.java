package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentCourseProgressDTO;
import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.entity.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student getOne(String id);
    List<Student> getAllStudents();
    Student insertOrUpdate(Student student, List<String> courseIds, String organizationId);
    boolean deleteStudent(String id);
    boolean deleteAllStudent();
    boolean enrollToCourse(String studentId, String courseId);
    boolean withdrawFromCourse(String studentId, String courseId);
    boolean updateCourseStatus(String studentId, String courseId, String status);
    List<StudentCourse> getStudentByProgress(String studentId);

}
