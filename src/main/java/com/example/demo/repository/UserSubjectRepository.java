package com.example.demo.repository;

import com.example.demo.domain.Subject;
import com.example.demo.domain.Type;
import com.example.demo.domain.User;
import com.example.demo.domain.UserSubject;
import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.SubjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

import java.util.Optional;

public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
    List<UserSubject> findAllBySubjectStatusAndMajorStatusAndUser(SubjectStatus subjectStatus, MajorStatus majorStatus, User user);
    List<UserSubject> findAllByUser(User user);

}
