package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.FeedbackType;
import lombok.Data;

@Data
public class TeacherApplyFeedbackRequest {
    private Long applicationId;
    private FeedbackType feedbackType;
}
