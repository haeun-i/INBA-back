package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ScoreDto {
    String totalScore; // 총이수학점
    String firstMajorScore; // 총주전공학점
    String firstMajorRequireScore; // 총주전공필수 학점
    String firstCoreRequireScore;  // 총주전공 교양필수학점
    String secondMajorScore; // 총부전공학점
    String secondMajorRequireScore; // 총부전공필수 학점
    String secondCoreRequireScore;  // 총부전공 교양필수학점
    String coreScore1; // 핵심교양1 학점
    String coreScore2; // 핵심교양2 학점
    String coreScore4; // 핵심교양4 학점
    String coreScore7; // 핵심교양전체 학점
    String creativeScore; // 창의 학점

    UserStatus userStatus; // 복부전 여부
    MajorStatus majorStatus; // 사용자의 과
    String userTest;
    String userEnglish;

    Boolean result;
}
