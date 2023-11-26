package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.FeedbackType;
import lombok.Data;

@Data
public class TeacherApplicationFeedbackRequest {
    private Long applicationId;

    private FeedbackType type;
}
