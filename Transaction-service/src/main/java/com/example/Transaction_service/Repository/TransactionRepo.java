package com.example.Transaction_service.Repository;

import com.example.Transaction_service.TransactionEntity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {

    // ✅ get all transactions for an account (as sender or receiver)
    List<TransactionEntity> findBySenderAccountIdOrReceiverAccountId(
            Long senderAccountId, Long receiverAccountId);
}