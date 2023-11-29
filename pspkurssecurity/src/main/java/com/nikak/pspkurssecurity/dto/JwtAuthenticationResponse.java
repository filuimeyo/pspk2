package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private String error;
}
