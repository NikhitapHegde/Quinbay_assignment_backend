package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {
    Organization getOne(String id);
    List<Organization> getAllOrganizations();
    Organization insertOrUpdate(Organization organization);
    boolean deleteOrganization(String id);
    Long getCountOfStudents(String id);
    Long getCountOfInstructors(String id);
    boolean deleteAllOrganizations();
   // Long getCountOfStudentsByCourse(String orgId);
    // public List<Student> getStudentsByCourses(String courses);
}
