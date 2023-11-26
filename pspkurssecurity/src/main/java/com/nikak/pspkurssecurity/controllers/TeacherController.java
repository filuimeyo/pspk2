package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.*;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.TeacherApplication;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final JWTService jwtService;
    private final TeacherService teacherService;

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
    public ResponseEntity<Teacher> updateTeacherProfile(
            @RequestBody TeacherProfileRequest teacherProfileRequest,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                teacherService.updateTeacherProfile(teacherProfileRequest, email)
        );
    }

    @PutMapping("/subjects")
    public ResponseEntity<String> assignSubjectForTeacher(
            @RequestBody Set<Long> subjectIds,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = teacherService.assignSubjects(subjectIds,email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @DeleteMapping("/subjects")
    public ResponseEntity<String> deleteSubjectForTeacher(
            @RequestBody Set<Long> subjectIds,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = teacherService.removeSubjects(subjectIds,email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }


    @PutMapping("/purposes")
    public ResponseEntity<String> assignPurposeForTeacher(
            @RequestBody Set<Long> purposesIds,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = teacherService.assignPurposes(purposesIds,email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @DeleteMapping("/purposes")
    public ResponseEntity<String> deletePurposesForTeacher(
            @RequestBody Set<Long> purposesIds,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = teacherService.removePurposes(purposesIds,email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }


    @DeleteMapping("/pic")
    public ResponseEntity<ResponseMessage> deleteTeacherPic(
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

    @GetMapping("/teacherapplications")
    public ResponseEntity<List<TeacherApplication>> getTeacherApplications(
            @RequestHeader("Authorization") String bearerToken
    ){
        // code that uses the language variable
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
               teacherService.getTeacherApplications(email)
        );
    }




    @PostMapping("/certificate")
    public ResponseEntity<String> addCertificate(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String bearerToken
    ){
        String email = jwtService.extractUserName(bearerToken.substring(7));
        String message = "";
        try {
            message = teacherService.addCertificate(email, file);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @DeleteMapping("/certificate")
    public ResponseEntity<String> deleteCertificate(
            @RequestBody Long certificateId,
            @RequestHeader("Authorization") String bearerToken
    ){
        String email = jwtService.extractUserName(bearerToken.substring(7));
        String message = "";
        try {
            message = teacherService.deleteCertificate(email, certificateId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }


    @PostMapping("/teacherapplicationfeedback")
    public ResponseEntity<String> addTeacherApplicationFeedback(
            @RequestBody TeacherApplicationFeedbackRequest request,
            @RequestHeader("Authorization") String bearerToken
    ){
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try{
            String message = teacherService.addTeacherApplicationFeedback(request, email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/subjectapplicationfeedback")
    public ResponseEntity<String> addTeacherApplicationFeedback(
            @RequestBody SubjectApplicationFeedbackReq request,
            @RequestHeader("Authorization") String bearerToken
    ){
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try{
            String message = teacherService.addSubjectApplicationFeedback(request, email);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

