package com.example.dev.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    public User(Object o, String username, String encode) {
        this.username = username;
        this.password = encode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
