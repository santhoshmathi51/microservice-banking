package com.example.user_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String bankName;
    private String branch;
    private String email;
    private Long accountNumber;
    private Double balance;
    private int age;


}