package com.example.dev.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reminder_rule")
public class ReminderRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long owner_id;
    private Long ownerType;
    private String title;
    private String Description;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP")
    private String createdAt;
    private String freq;
    private int Interval;
    private String interval_unit;
    private String status;
    private Boolean active;
}
