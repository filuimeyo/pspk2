package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Purpose;
import com.nikak.pspkurssecurity.services.PurposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurposeServiceImpl implements PurposeService {
    private final PurposeRepository purposeRepository;


    public Purpose addPurpose(String purpose) {
        Optional<Purpose> existingPurpose = purposeRepository.findByName(purpose);
        if (existingPurpose.isPresent()) {
            throw new IllegalStateException("purpose with name " + purpose + " already exists");
        }
        return purposeRepository.save(
                new Purpose(purpose)
        );
    }


    public Purpose updatePurpose(Long id, String purpose) {
        Purpose existingPurpose = purposeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("purpose with id: " + id + " does not exist"));
        existingPurpose.setPurpose(purpose);
        return purposeRepository.save(existingPurpose);
    }


    public String deletePurpose(Long id) {
        Purpose existingPurpose = purposeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("purpose with id: " + id + " does not exist"));
        purposeRepository.deleteById(id);
        return "purpose: "+ existingPurpose.getPurpose() + " deleted successfully";
    }
}
