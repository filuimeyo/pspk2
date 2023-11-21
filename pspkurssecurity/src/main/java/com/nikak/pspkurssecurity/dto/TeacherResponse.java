package com.nikak.pspkurssecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TeacherResponse {
    private Long id;
    private String name;
    private String info;
    private  double lessonPrice;
    private String url;

    private String filename;
    private String type;
    private long size;


}
