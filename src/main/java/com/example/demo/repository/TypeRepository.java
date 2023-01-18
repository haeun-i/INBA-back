package com.example.demo.repository;


import com.example.demo.domain.Subject;
import com.example.demo.domain.Type;
import com.example.demo.dto.SubjectStatus;
import com.example.demo.dto.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findBySubjectAndTypeYear(Subject subject, String typeYear);
    Optional<Type> findBySubjectAndTypeYearAndUserStatus(Subject subject, String typeYear, UserStatus userStatus);
    List<Type> findByTypeCodeAndTypeYear(SubjectStatus subjectStatus, String typeYear);
}