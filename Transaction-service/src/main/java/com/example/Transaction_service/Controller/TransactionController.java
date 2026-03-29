package com.example.Transaction_service.Controller;

import com.example.Transaction_service.DTO.TransactionRequest;
import com.example.Transaction_service.Repository.TransactionRepo;
import com.example.Transaction_service.Service.TransactionService;
import com.example.Transaction_service.TransactionEntity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepo transactionRepo;

    // ✅ trigger a transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionEntity> transfer(
            @RequestBody TransactionRequest request) {

        TransactionEntity result = transactionService.transfer(
                request.getSenderAccountId(),
                request.getReceiverAccountNumber(),
                request.getAmount()
        );
        return ResponseEntity.ok(result);
    }

    // ✅ get all transactions for an account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionEntity>> getByAccount(
            @PathVariable Long accountId) {
        return ResponseEntity.ok(
                transactionService.getTransactionsByAccount(accountId)
        );
    }
}