package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface SubjectService {
    String createSubject(MultipartFile file, String name) throws IOException;


    String updateSubjectImage(Long subjectId, MultipartFile file) throws IOException;

    String updateSubjectName(Long subjectId, String name);

    String deleteSubject(Long subjectId) throws IOException;
   // Subject getSubjectById(Long id);

    List<Object[]> findSubjectsWithCounts(String name);
    byte[] getSubjectImage(String filename) throws IOException;

    List<Subject> getMostPopularSubjects(PageRequest pageRequest);

    List<Subject> findAll();
}
