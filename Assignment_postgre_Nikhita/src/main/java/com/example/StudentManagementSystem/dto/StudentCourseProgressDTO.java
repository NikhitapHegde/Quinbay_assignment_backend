package com.example.StudentManagementSystem.dto;

public class StudentCourseProgressDTO {
    private Long courseId;
    private String status;

    public StudentCourseProgressDTO(Long courseId, String status) {
        this.courseId = courseId;
        this.status = status;
    }
    public StudentCourseProgressDTO() {
    }

        public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
