package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class userController {

    @RequestMapping("/register")
    public ResponseEntity<?> userRegister(RegisterRequest request){
        return ResponseEntity.ok(request.firstname() + " " + request.lastname() + " and email is" + request.email());
    }
}
