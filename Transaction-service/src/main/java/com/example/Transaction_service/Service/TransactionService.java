package com.example.Transaction_service.Service;

import com.example.Transaction_service.DTO.AccountDTO;
import com.example.Transaction_service.DTO.TransferRequest;
import com.example.Transaction_service.Fegin.AccountClient;
import com.example.Transaction_service.Repository.TransactionRepo;
import com.example.Transaction_service.TransactionEntity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountClient accountClient;

    // ✅ Full transfer flow
    public TransactionEntity transfer(Long senderAccountId,
                                      Long receiverAccountNumber,
                                      Double amount) {

        // Step 1 — get sender name from account-service using sender account id
        AccountDTO sender = accountClient.getById(senderAccountId);
        if (sender == null) {
            throw new RuntimeException("Sender account not found");
        }

        // Step 2 — get receiver name + id from account-service using account number
        AccountDTO receiver = accountClient.getByAccountNumber(receiverAccountNumber);
        if (receiver == null) {
            throw new RuntimeException("Receiver account not found");
        }

        // Step 3 — call account-service to debit sender and credit receiver
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderId(senderAccountId);
        transferRequest.setReceiverId(receiver.getId());   // ✅ use receiver's id
        transferRequest.setAmount(amount);

        accountClient.transfer(transferRequest);           // ✅ balance updated in account-service

        // Step 4 — save transaction record with names auto-fetched
        TransactionEntity txn = new TransactionEntity();
        txn.setSenderAccountId(senderAccountId);
        txn.setSenderName(sender.getFirstName());          // ✅ auto from account-service
        txn.setReceiverAccountId(receiver.getId());
        txn.setReceiverName(receiver.getFirstName());      // ✅ auto from account-service
        txn.setReceiverAccountNumber(receiverAccountNumber);
        txn.setAmount(amount);

        return transactionRepo.save(txn);
    }

    // ✅ get all transactions for an account
    public List<TransactionEntity> getTransactionsByAccount(Long accountId) {
        return transactionRepo
                .findBySenderAccountIdOrReceiverAccountId(accountId, accountId);
    }
}