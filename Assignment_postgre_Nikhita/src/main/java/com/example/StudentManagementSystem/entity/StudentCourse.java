package com.example.StudentManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class StudentCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties
    private Course course;

    public StudentCourse(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public StudentCourse() {
    }

        @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties
    private Student student;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
