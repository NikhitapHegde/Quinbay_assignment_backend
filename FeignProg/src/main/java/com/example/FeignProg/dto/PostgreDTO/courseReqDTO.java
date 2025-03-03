package com.example.FeignProg.dto.PostgreDTO;

public class courseReqDTO {
        private Long id;
        private String course_name;
        private double fee;
        private Long instructorID;
        private Long organizationID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Long instructorID) {
        this.instructorID = instructorID;
    }

    public Long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Long organizationID) {
        this.organizationID = organizationID;
    }
}

