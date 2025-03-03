package com.example.FeignProg.dto.MongoDTO;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;
public class InstructorDTO {
    @JsonIgnore
    private String id;

    private String name;

    private Date dateOfBirth;

    private OrganizationDTO organizationDTO;

    public OrganizationDTO getOrganizationDTO() {
        return organizationDTO;
    }

    public void setOrganizationDTO(OrganizationDTO organizationDTO) {
        this.organizationDTO = organizationDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
