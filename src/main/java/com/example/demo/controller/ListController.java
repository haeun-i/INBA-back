package com.example.demo.controller;

import com.example.demo.dto.SubjectDto;
import com.example.demo.service.ListService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="List")
@RequiredArgsConstructor
@RestController
@Log4j2
public class ListController {

    private final ListService listService;

    @GetMapping("/list/requireMajor")
    public ResponseEntity<List<SubjectDto>> requireMajorList() {
        return ResponseEntity.ok(listService.requireMajorList());
    }

    @GetMapping("/list/chooseMajor/computer")
    public ResponseEntity<List<SubjectDto>> chooseComputerMajorList() {
        return ResponseEntity.ok(listService.chooseComputerMajorList());
    }

    @GetMapping("/list/chooseMajor/second")
    public ResponseEntity<List<SubjectDto>> chooseSecondMajorList() {
        return ResponseEntity.ok(listService.chooseSecondMajorList());
    }

    @GetMapping("/list/requireMinor")
    public ResponseEntity<List<SubjectDto>> requireMinorList() {
        return ResponseEntity.ok(listService.requireMinorList());
    }

    @GetMapping("/list/core1")
    public ResponseEntity<List<SubjectDto>> core1List() {
        return ResponseEntity.ok(listService.core1List());
    }

    @GetMapping("/list/core2")
    public ResponseEntity<List<SubjectDto>> core2List() {
        return ResponseEntity.ok(listService.core2List());
    }

    @GetMapping("/list/core3")
    public ResponseEntity<List<SubjectDto>> core3List() {
        return ResponseEntity.ok(listService.core3List());
    }

    @GetMapping("/list/core4")
    public ResponseEntity<List<SubjectDto>> core4List() {
        return ResponseEntity.ok(listService.core4List());
    }

    @GetMapping("/list/core5")
    public ResponseEntity<List<SubjectDto>> core5List() {
        return ResponseEntity.ok(listService.core5List());
    }

    @GetMapping("/list/core6")
    public ResponseEntity<List<SubjectDto>> core6List() {
        return ResponseEntity.ok(listService.core6List());
    }

    @GetMapping("/list/creative")
    public ResponseEntity<List<SubjectDto>> creativeList() {
        return ResponseEntity.ok(listService.creativeList());
    }
}
