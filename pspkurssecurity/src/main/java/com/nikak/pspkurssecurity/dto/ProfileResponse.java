package com.nikak.pspkurssecurity.dto;

import com.nikak.pspkurssecurity.entities.*;
import lombok.Data;

import java.util.List;

@Data
public class ProfileResponse {
    private List<Application> applications;
    private List<Feedback> feedbacks;
    private User user;
    private Teacher teacher;
    private Role role;


    private List<Application> subjectApplications;
}
