package com.example.StudentManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@RedisHash("courses")
@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double fee;

    @OneToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("course")
    private Instructor instructor;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<StudentCourse> studentCourses;

    @ManyToMany(mappedBy = "courses") // Correct bidirectional mapping
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties
    private Organization organization;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fee=" + fee +
                ", instructor=" + instructor +
                '}';
    }
}
