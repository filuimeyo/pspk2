package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.*;
import com.nikak.pspkurssecurity.entities.Role;
import com.nikak.pspkurssecurity.entities.Student;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.User;
import com.nikak.pspkurssecurity.repositories.StudentRepository;
import com.nikak.pspkurssecurity.repositories.TeacherRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.AuthenticationService;
import com.nikak.pspkurssecurity.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public Role getRole(String email){

        var user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new IllegalArgumentException("Invalid email or password"));
        return user.getRole();
    }

    public JwtAuthenticationResponse signup(SignUpRequest signUpRequest){
        if(signUpRequest.getRole().equals(Role.ADMIN))
            throw new IllegalArgumentException("Can't sign up admin");

        Optional<User> existingUser = userRepository.findByEmail(signUpRequest.getEmail());
        if(existingUser.isPresent())
            throw  new IllegalStateException("email is taken");

        User user  = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole());
        User  newUser = userRepository.save(user);
        if(signUpRequest.getRole().equals(Role.STUDENT)){
            Student student = new Student();
            student.setName(signUpRequest.getName());
            student.setUser(newUser);
            studentRepository.save(student);
        }

        if(signUpRequest.getRole().equals(Role.TEACHER)){
            Teacher teacher = new Teacher();
            teacher.setName(signUpRequest.getName());
            teacher.setUser(newUser);
            teacherRepository.save(teacher);
        }


        var jwt = jwtService.generateToken(newUser);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getEmail(),
                        signinRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(()->
                        new IllegalArgumentException("Invalid email or password"));


        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }



    public JwtAuthenticationResponse changePassword(String email, ChangePasswordRequest changePasswordRequest) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}
