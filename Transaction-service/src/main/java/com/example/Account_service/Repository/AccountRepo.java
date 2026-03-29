package com.example.Account_service.Repository;

import com.example.Account_service.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByAccountNumber(Long accountNumber);
}