package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.entities.Rating;

import java.util.List;

public interface StudentService {
    String addRating(RatingRequest ratingRequest, String email);
    String deleteRatingById(Long id, String email);

    List<Object[]> getRatings(String email);
}
