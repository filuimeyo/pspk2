package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.entities.Purpose;

import java.util.List;

public interface PurposeService {
    Purpose addPurpose(String purpose);
    Purpose updatePurpose(Long id, String purpose);
    String deletePurpose(Long id);

    List<Purpose> findAll();
}
