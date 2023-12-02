package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.SubjectApplicationFeedbackReq;
import com.nikak.pspkurssecurity.dto.TeacherApplicationFeedbackRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.*;
import com.nikak.pspkurssecurity.repositories.*;
import com.nikak.pspkurssecurity.services.TeacherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final String FOLDER_PATH = "D:/Desktop/pspk2/teachers/";
    private final String FOLDER_PATH_CERTIFICATES = "D:/Desktop/pspk2/certificates/";
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherApplicationRepository teacherApplicationRepository;
    private final SubjectRepository subjectRepository;
    private final CertificateRepository certificateRepository;
    private final PurposeRepository purposeRepository;
    private final TeacherApplicationFeedbackRepository feedbackRepository;
    private final SubjectApplicationRepository subjectApplicationRepository;
    private final SubjectApplicationFeedbackRepository subjectFeedbackRepository;

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


    public List<Teacher> findTeachersBySubjectId(Long subjectId, Long purposeId, Long sort, boolean order) {
        Comparator<Teacher> finalRatingComp1 = Comparator.comparingDouble(Teacher::getFinalRating);
        Comparator<Teacher> finalRatingComp2 = (o1, o2) -> Double.compare(o2.getFinalRating(), o1.getFinalRating());

        if(purposeId==-1){
            if(sort == 1){
                return order? teacherRepository.findBySubjectIdSortByLessonPriceDesc(subjectId)
                        : teacherRepository.findBySubjectIdSortByLessonPriceAsc(subjectId);
            }else if(sort == 2){

                List<Teacher> list = teacherRepository.findBySubjectId(subjectId);
                if (order) {
                    list.sort(finalRatingComp2);
                } else {
                    list.sort(finalRatingComp1);
                }
                return list;

            } else throw new IllegalStateException("no such sort type");
        } else {
            if(sort == 1){
                return order? teacherRepository.findBySubjectIdAndPurposeIdSortByPriceDesc(subjectId, purposeId)
                        : teacherRepository.findBySubjectIdAndPurposeIdSortByPriceAsc(subjectId, purposeId);
            }else if(sort == 2){
                List<Teacher> list = teacherRepository.findBySubjectIdAndPurposeId(subjectId, purposeId);
                 if (order) {
                    list.sort(finalRatingComp2);
                } else {
                    list.sort(finalRatingComp1);
                }
                return list;

            } else throw new IllegalStateException("no such sort type");
        }

    }

    public byte[] getTeacherImage(String filename) throws IOException {
        Optional<Teacher> im = teacherRepository.findTeacherByFileName(filename);

        return Files.readAllBytes(
                new File(FOLDER_PATH + im.get().getFilename()).toPath());

    }

    public List<TeacherApplication> getTeacherApplications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        return teacherApplicationRepository.findByTeacherId(teacher.getId());
    }

    public String assignSubjects(Set<Long> subjectIds, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        Set<Subject> newset = teacher.getTeacherSubjects();
        for (Long id : subjectIds) {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("no subject with id " + id));
            newset.add(subject);
        }
        teacher.setTeacherSubjects(newset);
        teacherRepository.save(teacher);
        return "subject list updated successfully";
    }

    public String removeSubjects(Set<Long> subjectIds, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        Set<Subject> newset = teacher.getTeacherSubjects();
        for (Long id : subjectIds) {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("no subject with id " + id));
            newset.remove(subject);
        }
        teacher.setTeacherSubjects(newset);
        teacherRepository.save(teacher);
        return "subjects deleted from list  successfully";
    }

    public String addCertificate(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));

        if (file != null) {
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path saveTO = Paths.get(FOLDER_PATH_CERTIFICATES + filename);
            Files.copy(file.getInputStream(), saveTO);
            certificateRepository.save(
                    new Certificate(filename, teacher)
            );

            return "certificate added successfully";
        }
        return null;
    }

    public String deleteCertificate(String email, Long certificateId) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        Certificate certificate = certificateRepository.findByIdAndTeacher(certificateId, teacher.getId())
                .orElseThrow(() -> new IllegalStateException("no such certificate with id " + certificateId));
        Path saveTO = Paths.get(FOLDER_PATH_CERTIFICATES + certificate.getFileName());
        Files.delete(saveTO);
        certificateRepository.deleteById(certificateId);

        return "certificate deleted successfully";

    }

    public byte[] getCertificateImage(String filename) throws IOException {
        Optional<Certificate> im = certificateRepository.findCertificateByFileName(filename);

        return Files.readAllBytes(
                new File(FOLDER_PATH_CERTIFICATES + im.get().getFileName()).toPath());

    }

    public String assignPurposes(Set<Long> purposesIds, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        Set<Purpose> newset = teacher.getPurposes();
        for (Long id : purposesIds) {
            Purpose purpose = purposeRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("no purpose with id " + id));
            newset.add(purpose);
        }
        teacher.setPurposes(newset);
        teacherRepository.save(teacher);
        return "purpose list updated successfully";
    }

    public String removePurposes(Set<Long> purposesIds, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));
        Set<Purpose> newset = teacher.getPurposes();
        for (Long id : purposesIds) {
            Purpose purpose = purposeRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("no purpose with id " + id));
            newset.remove(purpose);
        }
        teacher.setPurposes(newset);
        teacherRepository.save(teacher);
        return "subjects deleted from list  successfully";
    }


    public String addTeacherApplicationFeedback(TeacherApplicationFeedbackRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));

        TeacherApplication existingApplication = teacherApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new IllegalStateException("no teacher applications with id " + request.getApplicationId()));

        Optional<TeacherApplicationFeedback> existingFeedback = feedbackRepository.findByApplicationId(request.getApplicationId());
        if (existingFeedback.isPresent()) {
            throw new IllegalStateException("feedback on this application already exist");
        }
        TeacherApplicationFeedback feedback = new TeacherApplicationFeedback();
        if (request.getType().equals(FeedbackType.OK)) {
            feedback.setFeedbackType(FeedbackType.OK);
            feedback.setApplication(existingApplication);
            feedback.setEmail(email);
            feedback.setApplicationDate(new Date());
        } else {
            feedback.setApplication(existingApplication);
            feedback.setFeedbackType(FeedbackType.REFUSED);
            feedback.setApplicationDate(new Date());
        }
        feedbackRepository.save(feedback);

        return "added feedback successfully";

    }


    public String addSubjectApplicationFeedback(SubjectApplicationFeedbackReq request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
            Teacher teacher = teacherRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new IllegalStateException("no such teacher with userid " + user.getId()));

        SubjectApplication subjectApplication = subjectApplicationRepository.findById(request.getSubjectApplicationId())
                .orElseThrow(() -> new IllegalStateException("no subject application found with id " + request.getSubjectApplicationId()));

        Optional<SubjectApplicationFeedback> existing = subjectFeedbackRepository
                .findByApplicationIdAndTeacherId(
                        request.getSubjectApplicationId(),
                        teacher.getId()
                );
        if (existing.isPresent()) {
            throw new IllegalStateException("feedback already exists");
        }

        SubjectApplicationFeedback subjectApplicationFeedback = new SubjectApplicationFeedback();
        subjectApplicationFeedback.setTeacher(teacher);
        subjectApplicationFeedback.setApplication(subjectApplication);
        subjectApplicationFeedback.setEmail(email);
        subjectApplicationFeedback.setApplicationDate(new Date());

        subjectFeedbackRepository.save(subjectApplicationFeedback);
        return "successfully added feedback on subject application";
    }

    public Optional<Teacher> findById(Long teacherId){
        return teacherRepository.findById(teacherId);
    }
}
