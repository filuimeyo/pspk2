package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.*;
import lombok.Data;

import java.util.List;

@Data
public class ProfileResponse {
    private List<Application> applications;
    private List<Feedback> feedbacks;
    private Role role;
}
