package com.nikak.pspkurssecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SubjectRequest {
    private String name;
    private MultipartFile file;
}
