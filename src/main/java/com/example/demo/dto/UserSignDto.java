package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignDto {
    String userEmail;
    String userLogpw;
    String userName;
    String userNum;
    UserStatus userStatus;
    MajorStatus userSmajor;
    Integer acceptYear;
    String userEnglish;
    String userTest;
    Boolean isChanging;
    MajorStatus pastMajor; // 전과 학과

}
