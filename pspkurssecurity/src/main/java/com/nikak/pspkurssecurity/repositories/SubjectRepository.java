package com.nikak.pspkurssecurity.repositories;

import com.nikak.pspkurssecurity.entities.Subject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s.id, s.name, s.filename, COALESCE(COUNT(t), 0)  FROM Subject s " +
            "left JOIN s.teachers t where s.name like %?1%" +
            " group by s order by COALESCE(COUNT(t), 0) DESC ")
    List<Object[]> findSubjectsWithCounts(String name); //для cтраницы со всеми предметами

    @Query("SELECT s, COALESCE(COUNT(t), 0)  FROM Subject s " +
            " left JOIN s.teachers t " +
            " group by s order by COALESCE(COUNT(t), 0) DESC ")
    List<Subject> findMostPopularSubjects(PageRequest pageRequest); //для главной

}
