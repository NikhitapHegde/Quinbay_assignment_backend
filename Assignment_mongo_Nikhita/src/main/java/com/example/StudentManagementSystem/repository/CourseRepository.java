package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findById(String Id);
}