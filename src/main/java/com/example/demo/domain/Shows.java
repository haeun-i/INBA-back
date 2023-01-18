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
@Table(name="shows")
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shows_id", nullable = false, unique = true)
    private Long showsId;

    @Column(name = "major_status", nullable = false)
    private MajorStatus majorStatus;

    @Column(name = "type_year", nullable = false)
    private String typeYear;

    @Column(name = "Student_status", nullable = false)
    private UserStatus userStatus;

    @Column(name = "graduate_string")
    private String graduateString;
}
