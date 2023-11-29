package com.nikak.pspkurssecurity.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class SubjectApplicationFeedback extends Feedback{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private SubjectApplication application;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private Date applicationDate;

    public SubjectApplicationFeedback() {
    }

    public SubjectApplicationFeedback(Long id, String email, SubjectApplication subjectApplication, Teacher teacher) {
        this.id = id;
        this.email = email;
        this.application = subjectApplication;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SubjectApplication getApplication() {
        return application;
    }

    public void setApplication(SubjectApplication subjectApplication) {
        this.application = subjectApplication;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectApplicationFeedback that = (SubjectApplicationFeedback) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(application, that.application) && Objects.equals(teacher, that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, application, teacher);
    }

    @Override
    public String toString() {
        return "SubjectApplicationFeedback{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", subjectApplication=" + application +
                ", teacher=" + teacher +
                '}';
    }
}
