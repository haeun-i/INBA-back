package com.example.demo.repository;

import com.example.demo.domain.Shows;
import com.example.demo.dto.MajorStatus;
import com.example.demo.dto.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;
import java.util.Optional;

public interface ShowsRepository extends JpaRepository<Shows, Long> {
    Optional<Shows> findByMajorStatusAndUserStatusAndTypeYear(MajorStatus m, UserStatus u, String y);
}