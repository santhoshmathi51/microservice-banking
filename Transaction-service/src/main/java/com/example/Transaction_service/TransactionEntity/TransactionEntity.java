package com.example.Transaction_service.TransactionEntity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long senderAccountId;
    private String senderName;

    private Long receiverAccountId;
    private String receiverName;
    private Long receiverAccountNumber;

    private Double amount;

    @Column(name = "date_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime dateTime;
}