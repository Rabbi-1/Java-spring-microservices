package com.rabbi.authservice.service;

import com.rabbi.authservice.dto.LoginRequestDTO;
import com.rabbi.authservice.util.JwtUtil;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
