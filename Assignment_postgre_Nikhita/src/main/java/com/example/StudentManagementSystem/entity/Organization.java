package com.example.StudentManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@RedisHash("organization")
@Entity
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("organization")
    private List<Student> students;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("organization")
    private List<Instructor> instructors;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("organization")
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", instructors=" + instructors +
                ", courses=" + courses +
                '}';
    }
}
