package com.example.dev.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "WeightLog")
public class WeightLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDate date;
    public Float weight;
    @Column(nullable = false)
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public WeightLog() {
    }

    public WeightLog(long id, LocalDate date, Float weight, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prepersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preupdate(){
        this.updatedAt = LocalDateTime.now();
    }


}
