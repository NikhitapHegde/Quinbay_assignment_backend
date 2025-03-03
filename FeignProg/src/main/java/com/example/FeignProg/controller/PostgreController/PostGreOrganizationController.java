package com.example.FeignProg.controller.PostgreController;

import com.example.FeignProg.dto.PostgreDTO.organizationDTO;
import com.example.FeignProg.service.PostgreService.organizationService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.FeignProg.service.PostgreService.organizationService;

@RestController
@RequestMapping("/postFeignOrg")
public class PostGreOrganizationController {
    @Autowired
    private organizationService organizationService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<organizationDTO>> saveOrg(@RequestBody organizationDTO organizationDTO) {
        return organizationService.savePostOrganization(organizationDTO);
    }
    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<organizationDTO>> getOrg(@RequestParam Long id) {
        return organizationService.getPostOne(id);
    }
}
