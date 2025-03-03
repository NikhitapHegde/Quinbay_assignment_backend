package com.example.StudentManagementSystem.dto;

public class StudentCourseProgressDTO {
    private String courseId;
    private String status;

    public StudentCourseProgressDTO(String courseId, String status) {
        this.courseId = courseId;
        this.status = status;
    }
    public StudentCourseProgressDTO() {
    }

        public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
