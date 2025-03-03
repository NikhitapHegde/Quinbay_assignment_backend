package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.entity.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstructorService {
    Instructor getOne(Long id);
    List<Instructor> getAllInstructors();
    Instructor insertOrUpdate(Instructor instructor, Long organizationId);
    boolean deleteInstructor(Long id);
    boolean deleteAllInstructors();
    boolean enrollToCourse(Long instructorId, Long courseId);
    boolean withdrawFromCourse(Long instructorId, Long courseId);
}
