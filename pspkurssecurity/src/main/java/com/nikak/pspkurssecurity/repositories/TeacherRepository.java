package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Subject;
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

    @Query("SELECT t from Teacher t inner join t.teacherSubjects s where s.id = ?1")
    List<Teacher> findBySubjectId(Long subjectId);

    @Query("SELECT t from Teacher t inner join t.teacherSubjects s where s.id = ?1 order by t.lessonPrice asc")
    List<Teacher> findBySubjectIdSortByLessonPriceAsc(Long subjectId);

    @Query("SELECT t from Teacher t inner join t.teacherSubjects s where s.id = ?1 order by t.lessonPrice desc")
    List<Teacher> findBySubjectIdSortByLessonPriceDesc(Long subjectId);


    @Query("SELECT t from Teacher t inner join t.teacherSubjects s inner join t.purposes p  where s.id = ?1 and p.id = ?2")
    List<Teacher> findBySubjectIdAndPurposeId(Long subjectId, Long purposeId);


    @Query("SELECT t from Teacher t inner join t.teacherSubjects s inner join t.purposes p  where s.id = ?1 and p.id = ?2 order by t.lessonPrice asc")
    List<Teacher> findBySubjectIdAndPurposeIdSortByPriceAsc(Long subjectId, Long purposeId);


    @Query("SELECT t from Teacher t inner join t.teacherSubjects s inner join t.purposes p  where s.id = ?1 and p.id = ?2 order by t.lessonPrice desc ")
    List<Teacher> findBySubjectIdAndPurposeIdSortByPriceDesc(Long subjectId, Long purposeId);

    @Query("SELECT t FROM Teacher t WHERE t.filename = ?1")
    Optional<Teacher> findTeacherByFileName(String filename);
}
