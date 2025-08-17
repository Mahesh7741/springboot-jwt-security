package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.dto.AssignProfileRequest;
import com.springsecurity.SpringSecurity.dto.GetAllProfile;
import com.springsecurity.SpringSecurity.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @GetMapping("/test")
    public String getAdminAcess(){
        return "access in admin route";
    }

    @GetMapping("/get-all-user-profile")
    public ResponseEntity<List<GetAllProfile>> getAllUserProfile(){
        return adminService.getAllUser();
    }

    @GetMapping("/get-all-admin-profile")
    public ResponseEntity<List<GetAllProfile>> getAllAdminProfile(){
        return adminService.getAllAdmin();
    }

    @PatchMapping("/assign-admin-profile")
    public ResponseEntity<?> assignAdminProfile(@RequestBody AssignProfileRequest request){
        return adminService.assignAdminProfile(request);
    }

    @PatchMapping("/assign-user-profile")
    public ResponseEntity<?> assignUserProfile(@RequestBody AssignProfileRequest request){
        return adminService.assignUserProfile(request);
    }
}
