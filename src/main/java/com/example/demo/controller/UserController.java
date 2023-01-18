package com.example.demo.controller;

import com.example.demo.auth.TokenProvider;
import com.example.demo.domain.User;
import com.example.demo.dto.*;
import com.example.demo.service.EmailService;
//import com.example.demo.service.OcrService;
import com.example.demo.service.OcrService;
import com.example.demo.service.S3Service;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.IOException;

@Api(tags="User")
@RequiredArgsConstructor
@RestController
@Log4j2
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final S3Service s3Service;
    private final OcrService ocrService;

    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignResultDto> sign(@RequestBody UserSignDto userSignDto){
        return ResponseEntity.ok(userService.signup(userSignDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("sendMail")
    public String mailConfirm(@RequestBody EmailDto emailDto) throws Exception {
        String confirm = emailService.sendSimpleMessage(emailDto.getEmail(), emailDto.getRandNum());
        return "success";
    }

    @PostMapping("/upload/pdf")
    public ResponseEntity<String> uploadPdf(@RequestPart("images") MultipartFile multipartFile) throws Exception {
        String path = s3Service.uploadS3Pdf(multipartFile);
        if(s3Service.uploadS3Pdf(multipartFile).equals("pdf 파일을 업로드해주세요")){
            throw new IllegalArgumentException("잘못된 파일입니다.");
        }
        return ResponseEntity.ok(path);
    }

    @PostMapping("/result/pdf")
    public ResponseEntity<RealShowDto> uploadFile(@RequestPart("images") MultipartFile multipartFile) throws Exception {

        String path = s3Service.uploadS3Pdf(multipartFile);
        List<String> stringList = s3Service.split(multipartFile);
        String num = ocrService.jsonParsingNum(ocrService.detectStudentNum(stringList.get(0)));
        List<ShowDto> list = ocrService.requireMajorList(ocrService.jsonParsing(ocrService.detectTextLeft(stringList.get(1))) + ocrService.jsonParsing(ocrService.detectTextRight(stringList.get(2))));

        RealShowDto realShowDto = RealShowDto.builder()
                .pdfPath(path)
                .studentNum(num)
                .showDtoList(list)
                .build();

        return ResponseEntity.ok(realShowDto);
    }

    // 복부전 상태 확인
    @GetMapping("/userStatus")
    public ResponseEntity<UserStatus> userStatus() throws Exception {
        return ResponseEntity.ok(userService.userStatus());
    }

}
