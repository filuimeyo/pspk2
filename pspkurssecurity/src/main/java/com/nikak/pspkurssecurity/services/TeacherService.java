package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface TeacherService {
   /* Teacher uploadPic(MultipartFile file, String email) throws IOException;


    Teacher getTeacherById(Long id);*/
   List<Teacher> findTeachersBySubjectId(Long subjectId);

    byte[] getTeacherImage(String filename) throws IOException;

    String updateTeacherImage(String email, MultipartFile file) throws IOException;

    String deleteTeacherImage(String email) throws IOException;
}
