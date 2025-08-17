package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.dto.LoginRequest;
import com.springsecurity.SpringSecurity.dto.RegisterRequest;
import com.springsecurity.SpringSecurity.entity.User;
import com.springsecurity.SpringSecurity.enums.Role;
import com.springsecurity.SpringSecurity.repository.UserRepository;
import com.springsecurity.SpringSecurity.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    @RequestMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody RegisterRequest request){
        if(userRepository.findByUsername(request.username()).isPresent() || userRepository.findByEmail(request.email()).isPresent()){
            return ResponseEntity.badRequest().body("user is already present");
        }

        User user=new User();

        user.setUsername(request.username());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return ResponseEntity.ok("user is register !!!");
    }

    @PostMapping("login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request){
        User user=userRepository.findByUsername(request.username()).orElseThrow(()->new RuntimeException("user is not found!!"));
        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            return ResponseEntity.badRequest().body("Invalid username or password !!!");
        }
        String token= jwtService.generateToken(request.username(), user.getRole().toString());
        return ResponseEntity.ok(Map.of("token", token));
    }

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
