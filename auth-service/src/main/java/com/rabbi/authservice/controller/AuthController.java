package com.rabbi.authservice.controller;

import com.rabbi.authservice.dto.LoginRequestDTO;
import com.rabbi.authservice.dto.LoginResponseDTO;
import com.rabbi.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {

        // Try to authenticate the user via authService
        // (authService should be injected as a dependency, but not shown here)
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        // If authentication fails, return 401 Unauthorized
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Wrap token in a response DTO and return 200 OK
        String token = tokenOptional.get(); // from optional to actual token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

