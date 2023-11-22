package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.SignUpRequest;
import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final JWTService jwtService;
    private final TeacherService teacherService;

    /*@PostMapping("pic")
    public ResponseEntity<ResponseMessage> uploadPic(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("file") MultipartFile file
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        String message = "";
        try {
            teacherService.uploadPic(file, email);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }*/

    @PutMapping("/pic")
    public ResponseEntity<ResponseMessage> updateTeacherPic(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        String message = "";
        try {
            message = teacherService.updateTeacherImage(email, file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<Teacher> updateTeacherPic(
            @RequestBody TeacherProfileRequest teacherProfileRequest,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                teacherService.updateTeacherProfile(teacherProfileRequest, email)
        );
    }

    @DeleteMapping("/pic")
    public ResponseEntity<ResponseMessage> updateTeacherPic(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        String message = "";
        try {
            message = teacherService.deleteTeacherImage(email);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete pic !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("")
    public ResponseEntity<String> greeting(
            @RequestHeader("Authorization") String bearerToken) {
        // code that uses the language variable
        String username = jwtService.extractUserName(bearerToken.substring(7));
        return new ResponseEntity<String>(username, HttpStatus.OK);
    }
}

