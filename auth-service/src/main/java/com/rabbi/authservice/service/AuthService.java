package com.rabbi.authservice.service;

import com.rabbi.authservice.dto.LoginRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService (UserService userService) {
        this.userService = userService;
    }
    /*
    Find the user by email.
    If no user → fails.
    If found, check the password.
    If password wrong → fail.
    If correct, → generate a token and return it.
    */
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(),
                        u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;

    }
}
//TODO: create password encoder 7,22,03