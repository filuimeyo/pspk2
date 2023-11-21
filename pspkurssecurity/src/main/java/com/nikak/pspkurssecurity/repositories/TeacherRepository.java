package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher  t where t.user.id = ?1")
    Optional<Teacher> findByUserId(Long userId);

    /*"SELECT teacher.*" +
            " from teacher" +
            " inner join teacher_subject" +
            " on teacher.id = teacher_subject.teacher_id" +
            " where subject_id = ?1",
    nativeQuery = true*/

    @Query("SELECT s.teachers  from Subject s where s.id = ?1")
    List<Teacher> findBySubjectId(Long subjectId);
}
