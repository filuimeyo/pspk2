package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.TeacherApplicationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherApplicationFeedbackRepository extends JpaRepository<TeacherApplicationFeedback, Long> {

    @Query("select t from TeacherApplicationFeedback t where t.application.id = ?1")
    Optional<TeacherApplicationFeedback> findByApplicationId(Long applicationId);
}
