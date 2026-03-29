package com.example.Account_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String bankName;
    private String branch;
    private String email;
    private String password;

    @Column(unique = true)
    private Long accountNumber;

    private Double balance;
    private int age;
}