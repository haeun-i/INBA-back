package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private Long subjectId;
    private String subjectNum;
    private String subjectName;
    private String subjectTime;
    private Integer subjectScore;
}
