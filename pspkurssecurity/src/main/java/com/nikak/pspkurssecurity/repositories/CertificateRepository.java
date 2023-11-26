package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query("SELECT c FROM Certificate c WHERE c.fileName = ?1")
    Optional<Certificate> findCertificateByFileName(String fileName);

    @Query("SELECT c FROM Certificate c WHERE c.id = ?1 and c.teacher.id = ?2")
    Optional<Certificate> findByIdAndTeacher(Long certificateId, Long teacherId);
}
