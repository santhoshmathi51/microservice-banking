package com.example.Account_service.Service;

import com.example.Account_service.Repository.AccountRepo;
import com.example.Account_service.entity.AccountEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public AccountEntity adduser(AccountEntity entity) {
        return accountRepo.save(entity);
    }

    public AccountEntity getuserdetail(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    public AccountEntity updateuser(Long id, AccountEntity updated) {
        AccountEntity existing = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setBankName(updated.getBankName());
        existing.setBranch(updated.getBranch());
        existing.setEmail(updated.getEmail());
        existing.setPassword(updated.getPassword());
        existing.setAccountNumber(updated.getAccountNumber());
        existing.setBalance(updated.getBalance());
        existing.setAge(updated.getAge());
        return accountRepo.save(existing);
    }

    public String deleteuser(Long id) {
        AccountEntity account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
        accountRepo.delete(account);
        return "Account deleted successfully";
    }

    public AccountEntity findByAccountNumber(Long accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber);
    }

    // ✅ transfer — debit sender, credit receiver, both saved
    @Transactional
    public void transferBalance(Long senderId, Long receiverId, Double amount) {
        AccountEntity sender = accountRepo.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        AccountEntity receiver = accountRepo.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepo.save(sender);
        accountRepo.save(receiver);
    }
}