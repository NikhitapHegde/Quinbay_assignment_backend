package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentCourseProgressDTO;
import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.entity.StudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student getOne(Long id);
    List<Student> getAllStudents();
    Student insertOrUpdate(Student student, List<Long> courseIds, Long organizationId);
    boolean deleteStudent(Long id);
    boolean deleteAllStudent();
    boolean enrollToCourse(Long studentId, Long courseId);
    boolean withdrawFromCourse(Long studentId, Long courseId);
    boolean updateCourseStatus(Long studentId, Long courseId, String status);
    List<StudentCourse> getStudentCourseProgress(Long studentId);
    //List<StudentDTO> getStudentByCourseStatus(String courseStatus);
}
