package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.SubjectRequest;
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

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final JWTService jwtService;
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<String> helloTeacher() {
        return ResponseEntity.ok("hello teacher");
    }

    @PostMapping("pic")
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

    }

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting(
            @RequestHeader("Authorization") String bearerToken) {
        // code that uses the language variable
        String username = jwtService.extractUserName(bearerToken.substring(7));
        return new ResponseEntity<String>(username, HttpStatus.OK);
    }
}

