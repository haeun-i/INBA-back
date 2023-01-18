package com.example.demo.controller;

import com.example.demo.dto.SubjectDto;
import com.example.demo.service.DefaultListService;
import com.example.demo.service.ListService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="DefaultList")
@RequiredArgsConstructor
@RestController
@Log4j2
public class DefaultListController {

    private final DefaultListService defaultListService;

    @GetMapping("/list/core1")
    public ResponseEntity<List<SubjectDto>> core1List() {
        return ResponseEntity.ok(defaultListService.core1List());
    }

    @GetMapping("/list/core2")
    public ResponseEntity<List<SubjectDto>> core2List() {
        return ResponseEntity.ok(defaultListService.core2List());
    }

    @GetMapping("/list/core3")
    public ResponseEntity<List<SubjectDto>> core3List() {
        return ResponseEntity.ok(defaultListService.core3List());
    }

    @GetMapping("/list/core4")
    public ResponseEntity<List<SubjectDto>> core4List() {
        return ResponseEntity.ok(defaultListService.core4List());
    }

    @GetMapping("/list/core5")
    public ResponseEntity<List<SubjectDto>> core5List() {
        return ResponseEntity.ok(defaultListService.core5List());
    }

    @GetMapping("/list/core6")
    public ResponseEntity<List<SubjectDto>> core6List() {
        return ResponseEntity.ok(defaultListService.core6List());
    }

    @GetMapping("/list/creative")
    public ResponseEntity<List<SubjectDto>> creativeList() {
        return ResponseEntity.ok(defaultListService.creativeList());
    }
}
