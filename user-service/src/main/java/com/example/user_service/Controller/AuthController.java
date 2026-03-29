package com.example.user_service.Controller;

import com.example.user_service.DTO.LoginRequest;
import com.example.user_service.Entity.UserEntity;
import com.example.user_service.Jwt.JwtToken;
import com.example.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    // ── Register — public ────────────────────────────────────
    // POST /api/auth/register?accountId=1
    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity entity,
                                               @RequestParam Long accountId) {
        return ResponseEntity.ok(userService.adduser(entity, accountId));
    }

    // ── Login — returns JWT ───────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(), req.getPassword()
                    )
            );
            if (auth.isAuthenticated()) {
                return ResponseEntity.ok(jwtToken.generateToken(req.getUsername()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}