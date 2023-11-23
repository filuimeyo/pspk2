package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.TeacherApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherApplicationRepository extends JpaRepository<TeacherApplication, Long> {

    @Query("select t from TeacherApplication  t where t.student.id = ?1")
    List<TeacherApplication> findByStudentId(Long studentId);

    @Query("select t from TeacherApplication  t where t.teacher.id = ?1")
    List<TeacherApplication> findByTeacherId(Long teacherId);
}
