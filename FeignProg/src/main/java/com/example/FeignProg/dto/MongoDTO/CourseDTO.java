package com.example.FeignProg.dto.MongoDTO;

//import com.example.StudentManagementSystem.entity.Instructor;
import net.minidev.json.annotate.JsonIgnore;

public class CourseDTO {
    @JsonIgnore
    private String id;

    private String name;

    private Double fee;

    private InstructorDTO instructorDTO;

    private OrganizationDTO organizationDTO;

    public CourseDTO(String id, String name, Double fee, InstructorDTO instructorDTO, OrganizationDTO organizationDTO) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.instructorDTO = instructorDTO;
        this.organizationDTO = organizationDTO;
    }

    public CourseDTO(){

    }

    public InstructorDTO getInstructorDTO() {
        return instructorDTO;
    }

    public void setInstructorDTO(InstructorDTO instructorDTO) {
        this.instructorDTO = instructorDTO;
    }

    public OrganizationDTO getOrganizationDTO() {
        return organizationDTO;
    }

    public void setOrganizationDTO(OrganizationDTO organizationDTO) {
        this.organizationDTO = organizationDTO;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
