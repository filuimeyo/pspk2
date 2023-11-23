package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.entities.Rating;
import com.nikak.pspkurssecurity.entities.Student;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.entities.User;
import com.nikak.pspkurssecurity.repositories.RatingRepository;
import com.nikak.pspkurssecurity.repositories.StudentRepository;
import com.nikak.pspkurssecurity.repositories.TeacherRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;

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
}
