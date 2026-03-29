package com.example.user_service.Controller;

import com.example.user_service.DTO.AccountDTO;
import com.example.user_service.Entity.UserEntity;
import com.example.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ── Get own registration record ───────────────────────────
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<UserEntity>> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.finduser(id));
    }

    // ── Get own ACCOUNT details from account-service via Feign ─
    // ✅ reads logged-in user from JWT → fetches only their account
    @GetMapping("/my-account")
    public ResponseEntity<AccountDTO> getMyAccount() {
        return ResponseEntity.ok(userService.getMyAccountDetails());
    }

    // ── Update own registration ───────────────────────────────
    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable Long id,
                                             @RequestBody UserEntity entity) {
        return ResponseEntity.ok(userService.updateuser(id, entity));
    }
}