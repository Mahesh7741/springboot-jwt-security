package com.springsecurity.SpringSecurity.service;

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
        List<GetAllProfile> newUsers =new ArrayList<>();

        for (User user : users) {
            if (user.getRole() == Role.ADMIN) {
                continue;
            }
            GetAllProfile newUser = new GetAllProfile(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(),user.getRole());
            newUsers.add(newUser);
        }
        return ResponseEntity.ok(newUsers);
    }

    public ResponseEntity<List<GetAllProfile>> getAllAdmin(){
        List<User> users = userRepository.findAll();
        List<GetAllProfile> newUsers =new ArrayList<>();

        for (User user : users) {
            if (user.getRole() != Role.ADMIN) {
                continue;
            }
            GetAllProfile newUser = new GetAllProfile(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(),user.getRole());
            newUsers.add(newUser);
        }
        return ResponseEntity.ok(newUsers);
    }

}
