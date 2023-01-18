package com.example.demo.controller;

import com.example.demo.auth.TokenProvider;
import com.example.demo.domain.User;
import com.example.demo.dto.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ExceptService;
import com.example.demo.service.SubjectService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Api(tags="Subject")
@RequiredArgsConstructor
@RestController
@Log4j2
public class SubjectController {

    private final SubjectService subjectService;
    private final ExceptService exceptService;
    private final UserRepository userRepository;

    @PostMapping("/save/single/{userId}")
    public ResponseEntity<String> save(@PathVariable Long userId, @RequestBody List<ShowDto> subjects) {
        return ResponseEntity.ok(subjectService.saveComputer(subjects, userId));
    }

    @PostMapping("/save/dual/{userId}")
    public ResponseEntity<String> dual(@PathVariable Long userId, @RequestBody List<ShowDto> subjects) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        if(user.getUserSmajor() == MajorStatus.글로벌금융학과){
            return ResponseEntity.ok(exceptService.saveFinance(subjects, userId));
        }else if(user.getUserSmajor() == MajorStatus.국제통상학과){
            return ResponseEntity.ok(exceptService.saveTrade(subjects, userId));
        }
        return ResponseEntity.ok(subjectService.saveDual(subjects, userId));
    }

    @GetMapping("/calculate")
    public ResponseEntity<ScoreDto> calculate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserEmail(authentication.getName()).get();

        if(user.getUserSmajor() == MajorStatus.산업경영공학과){
            return ResponseEntity.ok(exceptService.calculateIndustry());
        }
        return ResponseEntity.ok(subjectService.calculate());
    }






}
