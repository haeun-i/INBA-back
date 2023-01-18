package com.example.demo.domain;

import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.SubjectStatus;
import com.example.demo.dto.UserStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="standard")
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "standard_id", nullable = false, unique = true)
    private Long standardId;

    @Column(name = "standard_major")
    private MajorStatus majorStatus; // 무슨 과에서

    @Column(name = "student_status")
    private UserStatus studentStatus; // 복부전이

    @Column(name = "student_year")
    private String studentYear; // 몇년도에

    @Column(name = "ess_score")
    private Integer essScore;

    @Column(name = "maj_score")
    private Integer majScore;

    @Column(name = "edu_score")
    private Integer eduScore;

    @Column(name = "ess_list")
    private String essList;

    @Column(name = "maj_list")
    private String majList;

    @Column(name = "edu_list")
    private String eduList;

}
