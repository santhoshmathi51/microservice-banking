package com.example.user_service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    // ✅ just store accountId — no JPA join across services
    private Long accountId;
}