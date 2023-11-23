package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Rating;
import com.nikak.pspkurssecurity.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating  r where r.student.id = ?1 and r.teacher.id = ?2")
    Optional<Rating> findByStudentIdAndTeacherId(Long studentId, Long teacherId);

    @Query("SELECT r FROM Rating  r where r.id = ?1 and r.student.id = ?2")
    Optional<Rating> findByIdAndStudentId(Long id, Long studentId);

    @Query("SELECT r, r.teacher FROM Rating  r where r.student.id = ?1")
    List<Object[]> findByStudentId(Long studentId);
}
