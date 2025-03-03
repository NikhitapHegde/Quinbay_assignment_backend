package com.example.FeignProg.dto.PostgreDTO;

import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import net.minidev.json.annotate.JsonIgnore;
import nonapi.io.github.classgraph.json.Id;

public class courseDTO {
    @JsonIgnore
    private Long id;

    private String name;

    private Double fee;

    @JsonIgnore
    private InstructorDTO instructorDTO;

    private OrganizationDTO organizationDTO;

    public courseDTO(Long id, String name, Double fee, InstructorDTO instructorDTO, OrganizationDTO organizationDTO) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.instructorDTO = instructorDTO;
        this.organizationDTO = organizationDTO;
    }

    public courseDTO(){

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
