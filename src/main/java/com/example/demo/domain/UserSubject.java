package com.example.demo.domain;

import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.SubjectStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_subject")
public class UserSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_subject_id", nullable = false, unique = true)
    private Long UserSubjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "subject_num")
    private String subjectNum;

    @Column(name = "subject_score")
    private Integer subjectScore;

    @Column(name = "major_status")
    private MajorStatus majorStatus;

    @Column(name = "subject_status")
    private SubjectStatus subjectStatus;

}
