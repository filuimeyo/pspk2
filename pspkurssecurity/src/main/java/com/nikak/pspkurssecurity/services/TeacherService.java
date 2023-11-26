package com.nikak.pspkurssecurity.services;

import com.nikak.pspkurssecurity.dto.SubjectApplicationFeedbackReq;
import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.dto.TeacherApplicationFeedbackRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.TeacherApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface TeacherService {
    /* Teacher uploadPic(MultipartFile file, String email) throws IOException;


     Teacher getTeacherById(Long id);*/
    List<Teacher> findTeachersBySubjectId(Long subjectId);

    byte[] getTeacherImage(String filename) throws IOException;

    String updateTeacherImage(String email, MultipartFile file) throws IOException;

    String deleteTeacherImage(String email) throws IOException;

    Teacher updateTeacherProfile(TeacherProfileRequest teacherProfileRequest, String email);

    List<TeacherApplication> getTeacherApplications(String email);

    String assignSubjects(Set<Long> subjectIds, String email);

    String removeSubjects(Set<Long> subjectIds, String email);

    String addCertificate(String email, MultipartFile file) throws IOException;

    byte[] getCertificateImage(String filename) throws IOException;

    String deleteCertificate(String email, Long certificateId) throws IOException;

    String assignPurposes(Set<Long> purposesIds, String email);

    String removePurposes(Set<Long> purposesIds, String email);

    String addTeacherApplicationFeedback(TeacherApplicationFeedbackRequest request, String email);

    String addSubjectApplicationFeedback(SubjectApplicationFeedbackReq request, String email);
}
