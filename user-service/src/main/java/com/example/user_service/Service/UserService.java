package com.example.user_service.Service;

import com.example.user_service.DTO.AccountDTO;
import com.example.user_service.Entity.UserEntity;
import com.example.user_service.Fegin.AccountServiceClient;
import com.example.user_service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

  @Autowired
  private AccountServiceClient accountServiceClient;

    @Autowired
    private AuthUtil authUtil;

    public UserEntity adduser(UserEntity entity, Long accountId) {
        AccountDTO account = accountServiceClient.getAccountById(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account not found with id: " + accountId);
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setAccountId(accountId);
        return userRepo.save(entity);
    }

    public Optional<UserEntity> finduser(Long id) {
        return userRepo.findById(id);
    }

    public AccountDTO getMyAccountDetails() {
        String username = authUtil.getLoggedInUsername();
        UserEntity user = userRepo.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (user.getAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No account linked to this user");
        }
        return accountServiceClient.getAccountById(user.getAccountId());
    }

    public UserEntity updateuser(Long id, UserEntity updated) {
        UserEntity existing = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + id));

        String loggedIn = authUtil.getLoggedInUsername();
        if (!existing.getUsername().equals(loggedIn)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access denied: not your account");
        }
        existing.setUsername(updated.getUsername());
        existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        existing.setRole(updated.getRole());
        return userRepo.save(existing);
    }
}