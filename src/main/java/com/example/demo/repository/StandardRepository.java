package com.example.demo.repository;

import com.example.demo.domain.Standard;
import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StandardRepository extends JpaRepository<Standard, Long> {
    Optional<Standard> findByMajorStatusAndStudentStatusAndStudentYear(MajorStatus m, UserStatus u, String y);
}
