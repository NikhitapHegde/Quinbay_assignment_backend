package com.example.FeignProg.dto.MongoDTO;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import nonapi.io.github.classgraph.json.Id;

public class CourseReqDTO {
    private String id;
    private String course_name;
    private double fee;
    private String instructorID;
    private String organizationID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }
}