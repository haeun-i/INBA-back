package com.example.demo.domain;

import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.SubjectStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id", nullable = false, unique = true)
    private Long subjectId;

    @Column(name = "subject_num", nullable = false)
    private String subjectNum;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_time", nullable = false)
    private String subjectTime; // 이수학기

    @Column(name = "subject_score", nullable = false)
    private Integer subjectScore; // 인정학점

    @Column(name = "subject_major", nullable = false)
    private MajorStatus subjectMajor; // 개설학과

    @Column(name = "subject_now", nullable = false)
    private Integer subjectNow; // 현재 개설여부

    @Column(name = "subject_status", nullable = false)
    private SubjectStatus subjectStatus; // 핵교나 창의, 현장실습에 한함

    @Column(name = "subject_group")
    private Integer subjectGroup; // 현재 개설여부

}
