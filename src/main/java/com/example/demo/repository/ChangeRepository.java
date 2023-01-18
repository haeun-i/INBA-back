package com.example.demo.repository;

import com.example.demo.domain.Change;
import com.example.demo.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChangeRepository extends JpaRepository<Change, Long> {
    Optional<Change> findBySubjectNum(String subjectNum);
}
