package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {

    @Query("select p from Purpose p where p.purpose = ?1")
    Optional<Purpose> findByName(String purpose);
}
