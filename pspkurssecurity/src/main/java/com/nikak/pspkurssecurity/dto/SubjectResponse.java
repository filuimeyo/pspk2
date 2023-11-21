package com.nikak.pspkurssecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectResponse {
    private Long id;
    private String name;
    private String filename;
    private String url;
    private String type;
    private long size;
}
