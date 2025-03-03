package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Mongo-Organization", url = "http://localhost:8092/api/organization")
public interface OrganizationInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO);

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam String id);


}
