package com.nikak.pspkurssecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @GetMapping
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("hello student");
    }
}
