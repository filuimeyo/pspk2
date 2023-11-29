package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.services.AuthenticationService;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final JWTService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/role")
    public ResponseEntity<String> getRole(
            @RequestHeader("Authorization") String bearerToken
    ){
        try{

            String email = jwtService.extractUserName(bearerToken.substring(7));
            System.out.println(email);
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.getRole(email).name());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo(
            @RequestHeader("Authorization") String bearerToken
    ){
        try{

            String email = jwtService.extractUserName(bearerToken.substring(7));
            return ResponseEntity.status(HttpStatus.OK).body(userService.getProfileInfo(email));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
