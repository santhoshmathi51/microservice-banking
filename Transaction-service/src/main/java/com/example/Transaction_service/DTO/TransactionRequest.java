package com.example.Transaction_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Long senderAccountId;       // sender's account id
    private Long receiverAccountNumber; // receiver's account number
    private Double amount;
}