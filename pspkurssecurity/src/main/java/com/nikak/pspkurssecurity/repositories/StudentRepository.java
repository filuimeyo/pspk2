package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
