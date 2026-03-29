package com.example.Account_service.Controller;

import com.example.Account_service.DTO.AccountDto;
import com.example.Account_service.DTO.TransferRequest;
import com.example.Account_service.Repository.AccountRepo;
import com.example.Account_service.Service.AccountService;
import com.example.Account_service.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    @PostMapping("/post")
    public ResponseEntity<AccountEntity> adduser(@RequestBody AccountEntity entity) {
        return ResponseEntity.ok(accountService.adduser(entity));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountEntity> getuser(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getuserdetail(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountEntity> updateuser(@PathVariable Long id,
                                                    @RequestBody AccountEntity entity) {
        return ResponseEntity.ok(accountService.updateuser(id, entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteuser(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.deleteuser(id));
    }

    @GetMapping("/actnum/{actnum}")
    public ResponseEntity<AccountEntity> getuserbyactnum(@PathVariable Long actnum) {
        return ResponseEntity.ok(accountService.findByAccountNumber(actnum));
    }

    // ✅ Get account by id — called by transaction-service via Feign
    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable Long id) {
        AccountEntity acc = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return ResponseEntity.ok(new AccountDto(
                acc.getId(), acc.getAccountNumber(), acc.getFirstName()
        ));
    }

    // ✅ Get account by accountNumber — called by transaction-service via Feign
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountDto> getByAccountNumber(
            @PathVariable Long accountNumber) {
        AccountEntity acc = accountRepo.findByAccountNumber(accountNumber);
        if (acc == null) throw new RuntimeException("Account not found");
        return ResponseEntity.ok(new AccountDto(
                acc.getId(), acc.getAccountNumber(), acc.getFirstName()
        ));
    }

    // ✅ Transfer — called by transaction-service via Feign
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        accountService.transferBalance(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transfer Successful");
    }
}