package com.example.demo.controller;

import com.example.demo.dto.MyPageDto;
import com.example.demo.dto.RealShowDto;
import com.example.demo.dto.ShowDto;
import com.example.demo.dto.UserSignDto;
import com.example.demo.service.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="Mypage")
@RequiredArgsConstructor
@RestController
@Log4j2
public class MypageController {

    private final UserService userService;
    private final SubjectService subjectService;
    private final OcrService ocrService;
    private final S3Service s3Service;

    @GetMapping("/mypage")
    public ResponseEntity<UserSignDto> mypage() throws Exception {
        return ResponseEntity.ok(userService.showMypage());
    }

    @PostMapping("/modify/mypage")
    public ResponseEntity<HttpStatus> modify(@RequestBody MyPageDto myPageDto) throws Exception {
        userService.modify(myPageDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/reupload/pdf")
    public ResponseEntity<RealShowDto> uploadRePdf(@RequestPart("images") MultipartFile multipartFile) throws Exception {
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

    @PostMapping("/reupload/pdf/single/result")
    public ResponseEntity<String> uploadRePdfResult1(@RequestBody List<ShowDto> subjects) throws Exception {
        userService.deleteUserSubject();
        subjectService.saveComputer(subjects, userService.user().getUserId());
        return ResponseEntity.ok("success");
    }

    @PostMapping("/reupload/pdf/dual/result")
    public ResponseEntity<String> uploadRePdfResult2(@RequestBody List<ShowDto> subjects) throws Exception {
        userService.deleteUserSubject();
        subjectService.saveDual(subjects, userService.user().getUserId());
        return ResponseEntity.ok("success");
    }

}
