package com.example.dev.repository;

import com.example.dev.Entities.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightLogRepository  extends JpaRepository<WeightLog,Long> {
}
