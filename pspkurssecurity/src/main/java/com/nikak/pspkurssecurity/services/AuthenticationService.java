package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.*;
import com.nikak.pspkurssecurity.entities.Role;
import com.nikak.pspkurssecurity.entities.User;

public interface AuthenticationService {

    Role getRole(String email);
    JwtAuthenticationResponse signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    JwtAuthenticationResponse changePassword(String email, ChangePasswordRequest changePasswordRequest);
}
