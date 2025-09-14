package com.rabbi.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    private final Key secretKey;
//It takes the secret from the config, decodes it, and turns it into the key used to sign and verify JWT tokens.
    public JwtUtil(@Value("${jwt.secret}")String secret) {
        byte[] ketBytes = Base64.getDecoder().decode(secret.getBytes(
                StandardCharsets.UTF_8
        ));
        this.secretKey = Keys.hmacShaKeyFor(ketBytes);
    }
    /*It creates a JWT token that includes the
    user’s email and role, sets the issue time
    and an expiration (10 hours later), signs it
    with your secret key, and then returns the token
    as a string.
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey)
                .compact();
    }

}
