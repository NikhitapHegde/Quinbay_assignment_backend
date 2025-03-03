package com.example.FeignProg.controller.MongoController;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import com.example.FeignProg.service.MongoService.OrganizationService;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/postFeignOrg")
public class MongoOrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/saveOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO){
        return ResponseEntity.ok(Objects.requireNonNull(organizationService.saveMongoOrganization(organizationDTO).getBody()));
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam String id){
        return organizationService.getMongoOrganization(id);
    }
}
