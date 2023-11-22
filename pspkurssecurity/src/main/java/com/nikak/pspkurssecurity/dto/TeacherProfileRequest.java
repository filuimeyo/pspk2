package com.nikak.pspkurssecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TeacherProfileRequest {
    private String name;
    private String info;
    private Double lessonPrice;
    private Set<Long> subjectsIds;
}
