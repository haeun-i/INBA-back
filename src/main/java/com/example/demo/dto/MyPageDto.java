package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDto {
    UserStatus userStatus;
    MajorStatus userSmajor;
    Integer acceptYear;
    String userEnglish;
    String userTest;
}
