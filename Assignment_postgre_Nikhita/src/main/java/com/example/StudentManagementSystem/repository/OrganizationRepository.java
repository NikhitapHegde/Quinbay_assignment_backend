package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    // Count students by organization ID
    @Query("SELECT COUNT(s) FROM Student s WHERE s.organization.id = :orgId")
    Long countStudentsByOrgId(@Param("orgId") Long orgId);


    // Count instructors by organization ID
    @Query("SELECT COUNT(i) FROM Instructor i WHERE i.organization.id = :orgId")
    Long countInstructorsByOrgId(@Param("orgId") Long orgId);


    // Find organization by course ID
    @Query("SELECT c.organization FROM Course c WHERE c.id = :courseId")
    Organization findOrganizationByCourseId(@Param("courseId") Long courseId);


    // Count students by course ID
    @Query("SELECT COUNT(sc.student.id) FROM StudentCourse sc WHERE sc.course.id = :courseId")
    Long countStudentsByCourseId(@Param("courseId") Long courseId);


    // Get students by course ID
    @Query("SELECT sc.student FROM StudentCourse sc WHERE sc.course.id = :courseId")
    List<Student> getStudentsByCourseId(@Param("courseId") Long courseId);

}



