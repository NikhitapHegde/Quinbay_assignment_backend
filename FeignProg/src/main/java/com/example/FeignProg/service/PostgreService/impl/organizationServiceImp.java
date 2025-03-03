package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.PostgreDTO.organizationDTO;
import com.example.FeignProg.service.PostgreService.organizationService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class organizationServiceImp implements organizationService {

    @Autowired
    private com.example.FeignProg.feign.PostgreFeign.organizationInterface organizationInterface;

    @Override
    public ResponseEntity<ApiResponse<organizationDTO>> savePostOrganization(organizationDTO organizationDTO) {
        return organizationInterface.saveOrganization(organizationDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<organizationDTO>> getPostOne(Long orgId) {
        return organizationInterface.getOrgById(orgId);
    }
}
