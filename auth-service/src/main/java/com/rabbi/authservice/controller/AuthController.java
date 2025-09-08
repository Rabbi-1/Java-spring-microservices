package com.rabbi.authservice.controller;

import com.rabbi.authservice.dto.LoginRequestDTO;
import com.rabbi.authservice.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {  // Maps the request body JSON into a DTO object

        // Try to authenticate the user via authService
        // (authService should be injected as a dependency, but not shown here)
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        // If authentication fails, return 401 Unauthorized
        if(tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Wrap token in a response DTO and return 200 OK
        String token = tokenOptional.get(); // from optional to actual token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

