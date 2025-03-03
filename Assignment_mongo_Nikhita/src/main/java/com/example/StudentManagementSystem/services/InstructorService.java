package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.entity.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InstructorService {
    Instructor getOne(String id);
    List<Instructor> getAllInstructors();
    Instructor insertOrUpdate(Instructor instructor, String organizationId);
    boolean deleteInstructor(String id);
    boolean deleteAllInstructors();
    boolean enrollToCourse(String instructorId, String courseId);
    boolean withdrawFromCourse(String instructorId, String courseId);
}
