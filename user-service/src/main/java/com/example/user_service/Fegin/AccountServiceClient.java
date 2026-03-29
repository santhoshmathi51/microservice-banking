package com.example.user_service.Fegin;

import com.example.user_service.DTO.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountServiceClient {

    @GetMapping("/api/account/get/{id}")
    AccountDTO getAccountById(@PathVariable("id") Long id);
}