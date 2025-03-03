package com.example.FeignProg.controller.PostgreController;

import com.example.FeignProg.dto.PostgreDTO.instructorDTO;
import com.example.FeignProg.service.PostgreService.instructorService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postFeignInstructor")
public class PostGreInstructorController {

    @Autowired
    private instructorService instructorService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<instructorDTO>> saveStudent(@RequestBody instructorDTO instructorDTO) {
        return instructorService.savePostInstructor(instructorDTO);
    }
    @GetMapping("/getInstructor")
    public ResponseEntity<ApiResponse<instructorDTO>> getStud(@RequestParam Long id) {
        return instructorService.getPostInstructor(id);
    }
}
