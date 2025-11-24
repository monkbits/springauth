package com.example.dev.repository;

import com.example.dev.Entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Long> {

}
