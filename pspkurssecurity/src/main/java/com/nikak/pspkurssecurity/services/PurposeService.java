package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.entities.Purpose;

public interface PurposeService {
    Purpose addPurpose(String purpose);
    Purpose updatePurpose(Long id, String purpose);
    String deletePurpose(Long id);
}
