package com.example.demo.domain;

import com.example.demo.dto.SubjectStatus;
import com.example.demo.dto.UserStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false, unique = true)
    private Long typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "type_year", nullable = false)
    private String typeYear;

    @Column(name = "Student_status", nullable = false)
    private UserStatus userStatus;

    @Column(name = "type_code")
    private SubjectStatus typeCode;

}
