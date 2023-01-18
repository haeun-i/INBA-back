package com.example.demo.repository;

import com.example.demo.domain.Subject;
import com.example.demo.domain.User;
import com.example.demo.dto.SubjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectNum(String subjectNum);
    Optional<Subject> findBySubjectName(String subjectName);
    List<Subject> findAllBySubjectGroup(Integer subjectGroup);

    List<Subject> findAllBySubjectStatus(SubjectStatus subjectStatus);
}
