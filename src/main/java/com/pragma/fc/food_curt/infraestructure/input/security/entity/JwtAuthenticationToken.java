package com.pragma.fc.food_curt.infraestructure.input.security.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String token;

    public JwtAuthenticationToken(String principal, List<GrantedAuthority> roles, String token) {
        super(principal, null, roles);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
