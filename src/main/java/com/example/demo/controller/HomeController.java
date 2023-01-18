package com.example.demo.controller;

import com.example.demo.dto.SubjectDto;
import com.example.demo.service.CrawlingService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(tags="Home")
@RequiredArgsConstructor
@RestController
@Log4j2
public class HomeController {

    private final CrawlingService crawlingService;
    private final UserService userService;

    @GetMapping("/crawl")
    public ResponseEntity<List<List<String>>> noticeList() {
        return ResponseEntity.ok(crawlingService.process());
    }

    @GetMapping("/show")
    public ResponseEntity<List<String>> showGraduate() {
        return ResponseEntity.ok(userService.userGraduate());
    }

}
