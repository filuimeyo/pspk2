package com.nikak.pspkurssecurity.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.SubjectRequest;
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

    @PostMapping("/subject/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        String message = "";
        try {
            subjectService.addSubject(new SubjectRequest(name, file));

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
