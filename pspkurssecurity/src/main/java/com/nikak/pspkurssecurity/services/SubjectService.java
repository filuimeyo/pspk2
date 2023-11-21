package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface SubjectService {
    Subject addSubject(SubjectRequest subjectRequest) throws IOException;
    Subject getSubjectById(Long id);
    Stream<Subject> getAllFiles();

    List<Object[]> findSubjectsWithCounts(String name);
}
