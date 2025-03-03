package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId,Long courseId);
    List<StudentCourse> findByStudentId(Long studentId);
}
