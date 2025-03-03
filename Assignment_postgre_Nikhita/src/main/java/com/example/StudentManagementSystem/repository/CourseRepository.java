package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c " +
            "JOIN FETCH c.instructor i " +
            "JOIN FETCH c.organization o " +
            "WHERE c.id = :courseId")
    Course getCourseWithDetails(@Param("courseId") Long courseId);

}
