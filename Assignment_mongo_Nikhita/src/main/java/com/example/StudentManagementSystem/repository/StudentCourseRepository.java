package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface StudentCourseRepository extends MongoRepository<StudentCourse, String> {
    Optional<StudentCourse> findByStudentIdAndCourseId(String studentId,String courseId);
    List<StudentCourse> findByStudentId(String studentId);
    List<StudentCourse> findByStatus(String studentStatus);

}
