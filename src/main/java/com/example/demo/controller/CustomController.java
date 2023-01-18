package com.example.demo.controller;

import com.example.demo.dto.CustomDto;
import com.example.demo.dto.ScoreDto;
import com.example.demo.dto.SubjectDto;
import com.example.demo.service.CustomService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="Custom")
@RequiredArgsConstructor
@RestController
@Log4j2
public class CustomController {

    private final CustomService customService;

    @PostMapping("/custom/single")
    public ResponseEntity<ScoreDto> customComputer(@RequestBody List<CustomDto> customDtos) {
        return ResponseEntity.ok(customService.customComputer(customDtos));
    }

    @PostMapping("/custom/dual")
    public ResponseEntity<ScoreDto> customDual(@RequestBody List<CustomDto> customDtos) {
        return ResponseEntity.ok(customService.customDual(customDtos));
    }
}
