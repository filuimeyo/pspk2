package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.*;
import com.nikak.pspkurssecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/public/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(
            @RequestBody SignUpRequest signUpRequest
    ){
      return ResponseEntity.status(HttpStatus.OK)
                .header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.signup(signUpRequest));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(
            @RequestBody SigninRequest signinRequest
    ){
        System.out.println(signinRequest);
        return ResponseEntity.status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                //.header("Access-Control-Allow-Methods", "POST", "GET")
                .contentType(MediaType.APPLICATION_JSON)
                .body(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest
            ){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }






}
