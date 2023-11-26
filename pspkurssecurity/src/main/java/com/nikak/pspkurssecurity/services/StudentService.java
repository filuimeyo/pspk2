package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.ApplyForSubjectRequest;
import com.nikak.pspkurssecurity.dto.ApplyForTeacherRequest;
import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.Rating;
import com.nikak.pspkurssecurity.entities.TeacherApplication;

import java.util.List;

public interface StudentService {
    String addRating(RatingRequest ratingRequest, String email);
    String deleteRatingById(Long id, String email);

    List<Object[]> getRatings(String email);

    String applyForTeacher(ApplyForTeacherRequest request, String email);

    List<TeacherApplication> getTeacherApplications(String email);

    String applyForSubject(ApplyForSubjectRequest applyForSubjectRequest, String email);
}
