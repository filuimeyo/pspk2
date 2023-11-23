package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.ApplyForTeacherRequest;
import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.Rating;
import com.nikak.pspkurssecurity.entities.TeacherApplication;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final JWTService jwtService;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("hello student");
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Object[]>> getRatings(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                studentService.getRatings(email)
        );
    }

    @PutMapping("/rating")
    public ResponseEntity<String> addRating(
            @RequestBody RatingRequest ratingRequest,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        if (ratingRequest.getRating() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign null value "
            );
        } else if (ratingRequest.getRating() < 1 || ratingRequest.getRating() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign a rating greater than 10 or less than 1 "
            );
        }

        if (ratingRequest.getTeacherId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign a rating without specifying the teacher"
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                studentService.addRating(ratingRequest, email)
        );
    }

    @DeleteMapping("/rating/{ratingId}")
    public ResponseEntity<String> deleteRating(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long ratingId
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = studentService.deleteRatingById(ratingId, email);
            return ResponseEntity.status(HttpStatus.OK).body(
                    message
            );

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getMessage()
            );
        }

    }

    @GetMapping("/applyforteacher")
    public ResponseEntity<List<TeacherApplication>> getTeacherApplications(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                studentService.getTeacherApplications(email)
        );
    }


    @PostMapping("/applyforteacher")
    public ResponseEntity<String> applyForTeacher(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody ApplyForTeacherRequest applyForTeacherRequest
    ) {
        try {
            String email = jwtService.extractUserName(bearerToken.substring(7));
            return ResponseEntity.status(HttpStatus.OK).body(
                    studentService.applyForTeacher(applyForTeacherRequest, email)
            );

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getMessage()
            );
        }
    }
}
