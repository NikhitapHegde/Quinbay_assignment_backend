package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import com.example.FeignProg.service.MongoService.OrganizationService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.FeignProg.feign.MongoFeign.OrganizationInterface;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationInterface organizationInterface;

    public ResponseEntity<ApiResponse<OrganizationDTO>> saveMongoOrganization(OrganizationDTO organizationDTO){
        return organizationInterface.saveOrganization(organizationDTO);
    }

    public ResponseEntity<ApiResponse<OrganizationDTO>> getMongoOrganization(String orgId){
        return organizationInterface.getOrgById(orgId);
    }
}
