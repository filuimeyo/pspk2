package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.ProfileResponse;
import com.nikak.pspkurssecurity.entities.*;
import com.nikak.pspkurssecurity.repositories.*;
import com.nikak.pspkurssecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherApplicationRepository teacherApplicationRepository;
    private final SubjectApplicationRepository subjectApplicationRepository;
    private final TeacherApplicationFeedbackRepository teacherApplicationFeedbackRepository;
    private final SubjectApplicationFeedbackRepository subjectApplicationFeedbackRepository;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("User not found"));
            }
        };
    }


    public ProfileResponse getProfileInfo(String email) {
        Comparator<Application> comp = (o1, o2) -> o2.getApplicationDate().compareTo(o1.getApplicationDate());
        Comparator<Feedback> comp2 = (o1, o2) -> o2.getApplicationDate().compareTo(o1.getApplicationDate());

        ProfileResponse profileResponse = new ProfileResponse();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        profileResponse.setRole(user.getRole());
        switch (user.getRole()){
            case ADMIN -> {
                List<Application> applications = new ArrayList<>();
                applications.addAll(teacherApplicationRepository.findAll());
                applications.addAll(subjectApplicationRepository.findAll());
                applications.sort(comp);
                profileResponse.setApplications(applications);


                List<Feedback> feedbacks = new ArrayList<>();
                feedbacks.addAll(
                        teacherApplicationFeedbackRepository.findAll()
                );
                feedbacks.addAll(
                        subjectApplicationFeedbackRepository.findAll()
                );
                feedbacks.sort(comp2);
                profileResponse.setFeedbacks(feedbacks);

                break;
            }
            case STUDENT -> {
                Student student = studentRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));

                List<Application> applications = new ArrayList<>();
                applications.addAll(teacherApplicationRepository.findByStudentId(student.getId()));
                applications.addAll(subjectApplicationRepository.findByStudentId(student.getId()));
                applications.sort(comp);
                profileResponse.setApplications(applications);


                List<Feedback> feedbacks = new ArrayList<>();
                feedbacks.addAll(
                        teacherApplicationFeedbackRepository.findByStudentId(student.getId())
                );
                feedbacks.addAll(
                        subjectApplicationFeedbackRepository.findByStudentId(student.getId())
                );
                feedbacks.sort(comp2);
                profileResponse.setFeedbacks(feedbacks);

                break;
            }
            case TEACHER -> {
                break;
            }
        }
        return profileResponse;
    }
}
