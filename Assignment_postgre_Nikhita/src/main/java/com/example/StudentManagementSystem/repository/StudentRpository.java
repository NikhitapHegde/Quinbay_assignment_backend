package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRpository extends JpaRepository<Student, Long> {
    }

