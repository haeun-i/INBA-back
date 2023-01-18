package com.example.demo.domain;

import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.UserStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "user_num")
    private String userNum; // 스캔 결과로 받아올 유저의 학번

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_logpw", nullable = false)
    private String userLogpw;

    @Column(name = "user_name", nullable = false)
    private String userRealName;

    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus; // 복부전 여부

    @Column(name = "user_smajor")
    private MajorStatus userSmajor;

    @Column(name = "user_accept_year")
    private Integer acceptYear;

    @Column(name = "user_ischanging", nullable = false)
    private Boolean isChanging; // 전과 여부

    @Column(name = "user_pastmajor")
    private MajorStatus pastMajor; // 전과 학과

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserSubject> userSubjectList;

    @Column(name = "user_english", nullable = false)
    private String userEnglish;

    @Column(name = "user_test", nullable = false)
    private String userTest;

    public void updateUserStatus(UserStatus userStatus){
        this.userStatus = userStatus;
    }

    public void updateUserSmajor(MajorStatus majorStatus){
        this.userSmajor = userSmajor;
    }
    public void updateAcceptYear(Integer acceptYear){
        this.acceptYear = acceptYear;
    }
    public void updateUserEnglish(String userEnglish){
        this.userEnglish = userEnglish;
    }
    public void updateUserTest(String userTest){
        this.userTest = userTest;
    }


    // auth 관련 함수
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
