package com.example.FeignProg.dto.PostgreDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import net.minidev.json.annotate.JsonIgnore;

public class organizationDTO {
    @JsonIgnore

    private Long id;

    private String name;

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

    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
