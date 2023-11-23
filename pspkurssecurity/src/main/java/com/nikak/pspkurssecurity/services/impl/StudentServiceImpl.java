package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.ApplyForTeacherRequest;
import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.dto.TeacherProfileRequest;
import com.nikak.pspkurssecurity.entities.*;
import com.nikak.pspkurssecurity.repositories.*;
import com.nikak.pspkurssecurity.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherApplicationRepository teacherApplicationRepository;

    public String addRating(RatingRequest ratingRequest, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));

        Teacher teacher = teacherRepository.findById(ratingRequest.getTeacherId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with id " + ratingRequest.toString()));


        Optional<Rating> rating = ratingRepository.findByStudentIdAndTeacherId(student.getId(), teacher.getId());

        if(rating.isPresent()){
            rating.get().setRating(ratingRequest.getRating());
            rating.get().setComment(ratingRequest.getComment());
            ratingRepository.save(rating.get());
            return "rating updated successfully";
        } else {
            Rating newRating = new Rating();
            newRating.setStudent(student);
            newRating.setTeacher(teacher);
            newRating.setComment(ratingRequest.getComment());
            newRating.setRating(ratingRequest.getRating());
            ratingRepository.save(newRating);
            return "rating added successfully";
        }
    }

    public  String deleteRatingById(Long id, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));
        Rating rating = ratingRepository.findByIdAndStudentId(id, student.getId())
                .orElseThrow(()->new IllegalStateException("no rating with this id"));
        ratingRepository.deleteById(id);
        return "rating deleted successfully";
    }


    public List<Object[]> getRatings(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));

        return  ratingRepository.findByStudentId(student.getId());
    }

    public String applyForTeacher(ApplyForTeacherRequest request, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with id " + request.getTeacherId()));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(()-> new IllegalStateException("no subject with such id"));
        if(teacher.getTeacherSubjects().contains(subject)){
            TeacherApplication teacherApplication = new TeacherApplication();
            teacherApplication.setStudent(student);
            teacherApplication.setTeacher(teacher);
            teacherApplication.setSubject(subject);
            teacherApplication.setApplicationDate(new Date());
            teacherApplicationRepository.save(teacherApplication);
            return "successfully applied for teacher: "+ teacherApplication.getTeacher().getName()
                    + " with subject:" + teacherApplication.getSubject().getName();
        }else throw new IllegalStateException("teacher does not teach this subject");
    }


    @Override
    public List<TeacherApplication> getTeacherApplications(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such student with userid " + user.getId()));

        return teacherApplicationRepository.findByStudentId(student.getId());
    }
}
