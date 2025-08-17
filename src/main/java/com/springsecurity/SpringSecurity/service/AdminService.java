package com.springsecurity.SpringSecurity.service;

import com.springsecurity.SpringSecurity.dto.AssignProfileRequest;
import com.springsecurity.SpringSecurity.dto.GetAllProfile;
import com.springsecurity.SpringSecurity.entity.User;
import com.springsecurity.SpringSecurity.enums.Role;
import com.springsecurity.SpringSecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public ResponseEntity<List<GetAllProfile>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<GetAllProfile> newUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getRole() == Role.ADMIN) {
                continue;
            }
            GetAllProfile newUser = new GetAllProfile(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole());
            newUsers.add(newUser);
        }
        return ResponseEntity.ok(newUsers);
    }

    public ResponseEntity<List<GetAllProfile>> getAllAdmin() {
        List<User> users = userRepository.findAll();
        List<GetAllProfile> newUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getRole() != Role.ADMIN) {
                continue;
            }
            GetAllProfile newUser = new GetAllProfile(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole());
            newUsers.add(newUser);
        }
        return ResponseEntity.ok(newUsers);
    }


    public ResponseEntity<?> assignAdminProfile(AssignProfileRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getEmail().equals(request.email())) {
            return ResponseEntity.badRequest().body("User email does not match");
        }
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.badRequest().body("User is already an admin");
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return ResponseEntity.ok("User promoted to ADMIN successfully");
    }

    public ResponseEntity<?> assignUserProfile(AssignProfileRequest request) {
        User admin=userRepository.findByUsername(request.username()).orElseThrow(()-> new RuntimeException("Admin is not found"));
        if(!admin.getEmail().equals(request.email())){
            return ResponseEntity.badRequest().body("Admin email does not match");
        }
        if(admin.getRole()==Role.USER){
            return ResponseEntity.badRequest().body("User is already an User");
        }
        admin.setRole(Role.USER);
        userRepository.save(admin);
        return ResponseEntity.ok("User promoted to USER successfully");
    }
}
