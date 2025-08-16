package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.dto.LoginRequest;
import com.springsecurity.SpringSecurity.dto.RegisterRequest;
import com.springsecurity.SpringSecurity.entity.User;
import com.springsecurity.SpringSecurity.enums.Role;
import com.springsecurity.SpringSecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class userController {

    @Autowired
    private final UserRepository userRepository;

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

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request){

        User user=userRepository.findByUsername(request.username()).orElseThrow(()->new RuntimeException("user is not found"));

        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            return ResponseEntity.badRequest().body("Invaild email or password");
        }

        return ResponseEntity.ok("user is login" +" " + user.getFirstname()+" "+user.getLastname());
    }
}
