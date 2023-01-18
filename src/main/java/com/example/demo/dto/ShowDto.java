package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto {
    private Integer index;
    private String subjectNum;
    private String subjectName;
    private String subjectScore;
    private String studentScore;
}
