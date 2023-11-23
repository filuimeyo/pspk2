package com.nikak.pspkurssecurity.dto;

import lombok.Data;

@Data
public class RatingRequest {
    private Long teacherId;
    private Integer rating;
    private String comment;
}
