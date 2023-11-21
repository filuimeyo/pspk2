package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import com.nikak.pspkurssecurity.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private final SubjectRepository subjectRepository;

    public Subject addSubject(SubjectRequest subjectRequest) throws IOException {
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());

        if(subjectRequest.getFile() != null){
            String filename = StringUtils.cleanPath(subjectRequest.getFile().getOriginalFilename());
            subject.setFilename(filename);
            subject.setType(subjectRequest.getFile().getContentType());
            subject.setData(subjectRequest.getFile().getBytes());
        }

        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(Long id){
        return subjectRepository.findById(id).get();
    }

    public Stream<Subject> getAllFiles() {
        return subjectRepository.findAll().stream();
    }

    public List<Object[]> findSubjectsWithCounts(String name){
        if (name == null) name = "";
        return subjectRepository.findSubjectsWithCounts(name);
    }
}
