package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.User;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import com.nikak.pspkurssecurity.repositories.TeacherRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.TeacherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final String FOLDER_PATH = "D:/Desktop/pspk2/teachers/";
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;


    public String updateTeacherImage(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));

        if (file != null) {
            if (teacher.getFilename() != null) {

                String filename = teacher.getFilename();
                Path saveTO = Paths.get(FOLDER_PATH + filename);
                Files.delete(saveTO);
                Files.copy(file.getInputStream(), saveTO);
            }
            if (teacher.getFilename() == null) {
                String filename = UUID.randomUUID().toString() + ".jpg";
                Path saveTO = Paths.get(FOLDER_PATH + filename);

                teacher.setFilename(filename);
                // savedData =  subjectRepository.save(subjectToSave);
                Files.copy(file.getInputStream(), saveTO);
            }
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        if (savedTeacher != null) return "teacher pic : " + savedTeacher.getFilename() + " updated successfully";
        return null;

    }

    @Transactional
    public Teacher updateTeacherProfile(TeacherProfileRequest teacherProfileRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        System.out.println("user found");
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        System.out.println("teacher found");
        if (teacherProfileRequest.getName() != null) {
            teacher.setName(teacherProfileRequest.getName());
        }
        if (teacherProfileRequest.getInfo() != null) {
            teacher.setInfo(teacherProfileRequest.getInfo());
        }
        if (teacherProfileRequest.getLessonPrice() != null) {
            teacher.setLessonPrice(teacherProfileRequest.getLessonPrice());
        }
       /* if (teacherProfileRequest.getSubjectsIds() != null) {
            List<Subject> subjects = teacher.getTeacherSubjects();
            for (Long id : teacherProfileRequest.getSubjectsIds()) {

                Optional<Subject> subject = subjectRepository.findById(id);
                if(subject.isPresent()) {
                    if(!subjects.contains(subject.get())){
                        subjects.add(subject.get());
                    }
                }
            }
            teacher.setTeacherSubjects(subjects);
        }*/
        return teacherRepository.save(teacher);

    }

    public String deleteTeacherImage(String email) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));

        if (teacher.getFilename() != null) {
            Path saveTO = Paths.get(FOLDER_PATH + teacher.getFilename());
            Files.delete(saveTO);
            teacher.setFilename(null);
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        if (savedTeacher != null) return "teacher pic deleted successfully";
        return null;
    }
    /*public Teacher uploadPic(MultipartFile file, String email) throws IOException{

        User user  = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        System.out.println(user);
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid "+ user.getId()));
        System.out.println(teacher);
        if(file != null){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            teacher.setFilename(filename);
            teacher.setType(file.getContentType());
            teacher.setData(file.getBytes());
        }

        return teacherRepository.save(teacher);
    }*/


    public List<Teacher> findTeachersBySubjectId(Long subjectId) {
        return teacherRepository.findBySubjectId(subjectId);
    }

    public byte[] getTeacherImage(String filename) throws IOException {
        Optional<Teacher> im = teacherRepository.findTeacherByFileName(filename);

        return Files.readAllBytes(
                new File(FOLDER_PATH + im.get().getFilename()).toPath());

    }

}
