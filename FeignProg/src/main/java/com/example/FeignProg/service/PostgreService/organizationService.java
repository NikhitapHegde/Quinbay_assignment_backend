package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.PostgreDTO.organizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface organizationService {
    public ResponseEntity<ApiResponse<organizationDTO>> savePostOrganization(organizationDTO organizationDTO);
    public ResponseEntity<ApiResponse<organizationDTO>> getPostOne(Long orgId);
}
