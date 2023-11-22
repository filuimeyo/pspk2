package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import com.nikak.pspkurssecurity.services.SubjectService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("hello admin");
    }

    @PostMapping("/subject")
    public ResponseEntity<ResponseMessage> uploadFile(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name) {
        String message = "";
        try {
            message =  subjectService.createSubject(file, name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/subject/pic/{subjectId}")
    public ResponseEntity<ResponseMessage> updateSubjectPic(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long subjectId
    ){
        String message = "";
        try {
            message =  subjectService.updateSubjectImage(subjectId,file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/subject/name/{subjectId}")
    public ResponseEntity<ResponseMessage> updateSubjectName(
            @RequestParam(value = "name", required = false) String name,
            @PathVariable Long subjectId
    ){
        String message = "";
        try {
            message =  subjectService.updateSubjectName(subjectId, name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update subject name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/subject/{subjectId}")
    public ResponseEntity<ResponseMessage> deleteSubject(
            @PathVariable Long subjectId
    ){
        String message = "";
        try {
            message =  subjectService.deleteSubject(subjectId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update subject name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
