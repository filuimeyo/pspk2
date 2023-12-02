package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.TeacherApplicationFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherApplicationFeedbackRepository extends JpaRepository<TeacherApplicationFeedback, Long> {

    @Query("select t from TeacherApplicationFeedback t where t.application.id = ?1")
    Optional<TeacherApplicationFeedback> findByApplicationId(Long applicationId);

    @Query("select t from TeacherApplicationFeedback t where t.application.student.id = ?1")
    List<TeacherApplicationFeedback> findByStudentId(Long studentId);

    @Query("select t from TeacherApplicationFeedback t where t.application.teacher.id = ?1")
    List<TeacherApplicationFeedback> findByTeacherId(Long teacherId);
}
