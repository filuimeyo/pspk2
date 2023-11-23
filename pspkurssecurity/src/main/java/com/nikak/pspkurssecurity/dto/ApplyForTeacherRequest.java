package com.nikak.pspkurssecurity.dto;

import lombok.Data;

@Data
public class ApplyForTeacherRequest {
    private Long teacherId;
    private Long subjectId;
}
