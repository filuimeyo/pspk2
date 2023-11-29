package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.JwtAuthenticationResponse;
import com.nikak.pspkurssecurity.dto.RefreshTokenRequest;
import com.nikak.pspkurssecurity.dto.SignUpRequest;
import com.nikak.pspkurssecurity.dto.SigninRequest;
import com.nikak.pspkurssecurity.entities.Role;
import com.nikak.pspkurssecurity.entities.User;

public interface AuthenticationService {

    Role getRole(String email);
    JwtAuthenticationResponse signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
