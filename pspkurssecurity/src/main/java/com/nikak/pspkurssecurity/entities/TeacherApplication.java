package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.Date;


@Entity
public class TeacherApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    @JsonManagedReference
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    @JsonManagedReference
    private Student student;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    @JsonManagedReference
    private Subject subject;


    @JsonIgnore
    @OneToOne(mappedBy = "application")
    private TeacherApplicationFeedback feedback;

    private Date applicationDate;

    public TeacherApplication() {
    }

    public TeacherApplication(Teacher teacher, Student student, Subject subject, TeacherApplicationFeedback feedback, Date applicationDate) {
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.feedback = feedback;
        this.applicationDate = applicationDate;
    }

    public TeacherApplication(Long id, Teacher teacher, Student student, Subject subject, TeacherApplicationFeedback feedback, Date applicationDate) {
        this.id = id;
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.feedback = feedback;
        this.applicationDate = applicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public TeacherApplicationFeedback getFeedback() {
        return feedback;
    }

    public void setFeedback(TeacherApplicationFeedback feedback) {
        this.feedback = feedback;
    }
}
