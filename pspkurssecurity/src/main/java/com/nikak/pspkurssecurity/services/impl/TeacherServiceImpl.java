package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.User;
import com.nikak.pspkurssecurity.repositories.TeacherRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.TeacherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    /*public void addSubject(MultipartFile file, String token) throws IOException {
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());

        if(file != null){
            String filename = StringUtils.cleanPath(subjectRequest.getFile().getOriginalFilename());
            subject.setFilename(filename);
            subject.setType(subjectRequest.getFile().getContentType());
            subject.setData(subjectRequest.getFile().getBytes());
        }

        return subjectRepository.save(subject);
    }*/

    public Teacher uploadPic(MultipartFile file, String email) throws IOException{
        User user  = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid "+ user.getId()));

        if(file != null){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            teacher.setFilename(filename);
            teacher.setType(file.getContentType());
            teacher.setData(file.getBytes());
        }

        return teacherRepository.save(teacher);
    }

    @Transactional
    public Stream<Teacher> findBySubjectId(Long subjectId){
        return teacherRepository.findBySubjectId(subjectId).stream();
    }

    public Teacher getTeacherById(Long id){
        return teacherRepository.findById(id).get();
    }


}
