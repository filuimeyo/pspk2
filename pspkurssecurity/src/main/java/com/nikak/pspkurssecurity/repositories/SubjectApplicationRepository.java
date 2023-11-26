package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.SubjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubjectApplicationRepository extends JpaRepository<SubjectApplication, Long> {
    @Query("select s from  SubjectApplication  s where s.student.id = ?1")
    List<SubjectApplication> findByStudentId(Long studentId, Long subjectId);

}
