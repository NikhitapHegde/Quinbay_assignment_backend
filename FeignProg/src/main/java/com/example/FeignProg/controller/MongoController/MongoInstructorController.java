package com.example.FeignProg.controller.MongoController;

import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.service.MongoService.InstructorService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/postFeignInstructor")
public class MongoInstructorController {

    @Autowired
    private InstructorService instructorService;
    @PostMapping("/saveInstructor")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveOrganization(@RequestBody InstructorDTO instructorDTO) {
        return ResponseEntity.ok(Objects.requireNonNull(instructorService.saveMongoInstructor(instructorDTO).getBody()));
    }
    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<InstructorDTO>> getOrgById(@RequestParam String id) {
        return instructorService.getMongoInstructor(id);
    }
}
