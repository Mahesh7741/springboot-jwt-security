package com.springsecurity.SpringSecurity.dto;

import com.springsecurity.SpringSecurity.enums.Role;

public record GetAllProfile(Long id, String username, String firstname, String lastname, String email, Role role) {
}
