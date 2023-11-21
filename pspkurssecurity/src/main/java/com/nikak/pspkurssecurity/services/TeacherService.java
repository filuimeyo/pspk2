package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface TeacherService {
    Teacher uploadPic(MultipartFile file, String email) throws IOException;
    Stream<Teacher> findBySubjectId(Long subjectId);

    Teacher getTeacherById(Long id);
}
