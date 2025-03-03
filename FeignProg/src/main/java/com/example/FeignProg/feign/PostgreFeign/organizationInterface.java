package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.PostgreDTO.organizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Postgre-Organization", url = "http://localhost:8084/api/organization")
public interface organizationInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<organizationDTO>> saveOrganization(@RequestBody organizationDTO organizationDTO);

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<organizationDTO>> getOrgById(@RequestParam Long id);

}
