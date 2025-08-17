package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.repository.UserRepository;
import com.springsecurity.SpringSecurity.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    @GetMapping("/profile")
    public ResponseEntity<?> getUsername(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        return ResponseEntity.ok("Profile of " + username + " - Token is working!");
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String authHeader){
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok("Token is valid for user: " + username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token validation failed: " + e.getMessage());
        }
    }
}
