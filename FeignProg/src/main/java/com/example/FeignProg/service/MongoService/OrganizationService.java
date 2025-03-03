package com.example.FeignProg.service.MongoService;

import com.example.FeignProg.dto.MongoDTO.OrganizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrganizationService {
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveMongoOrganization(OrganizationDTO organizationDTO);
    public ResponseEntity<ApiResponse<OrganizationDTO>> getMongoOrganization(String orgId);
}
