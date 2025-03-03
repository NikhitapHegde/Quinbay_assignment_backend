package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface OrganizationService {
    Organization getOne(Long id);
    List<Organization> getAllOrganizations();
    Organization insertOrUpdate(Organization organization);
    boolean deleteOrganization(Long id);
    Long getCountOfStudents(Long id);
    Long getCountOfInstructors(Long id);
    boolean deleteAllOrganizations();
    Long getCountOfStudentsByCourse(Long orgId);
    public StudentDTO getStudentsByCourseId(Long id);
}
