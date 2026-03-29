package com.example.Transaction_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private Long senderId;
    private Long receiverId;
    private Double amount;
}