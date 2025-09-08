package com.rabbi.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponseDTO {
    private final String token;
    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
