package com.example.Transaction_service.Fegin;

import com.example.Transaction_service.DTO.AccountDTO;
import com.example.Transaction_service.DTO.TransferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    // ✅ get sender by account id
    @GetMapping("/api/account/id/{id}")
    AccountDTO getById(@PathVariable("id") Long id);

    // ✅ get receiver by account number
    @GetMapping("/api/account/number/{accountNumber}")
    AccountDTO getByAccountNumber(@PathVariable("accountNumber") Long accountNumber);

    // ✅ trigger balance transfer in account-service
    @PostMapping("/api/account/transfer")
    void transfer(@RequestBody TransferRequest request);
}