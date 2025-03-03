package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository

public interface OrganizationRepository extends MongoRepository<Organization, String> {

    long countById(String orgId);
    List<Student> findStudentsById(String orgId);
    List<Instructor> findInstructorsById(String orgId);
    boolean existsById(String orgId);
}



