package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.SubjectApplicationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubjectApplicationFeedbackRepository extends JpaRepository<SubjectApplicationFeedback, Long> {

    @Query("select s from SubjectApplicationFeedback  s where s.subjectApplication.id = ?1 and s.teacher.id = ?2")
    Optional<SubjectApplicationFeedback> findByApplicationIdAndTeacherId(Long applId, Long teacherId);
}
