package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student  s where s.user.id = ?1")
    Optional<Student> findByUserId(Long userId);
}
