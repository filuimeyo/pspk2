package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class SubjectApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    @JsonManagedReference
    private Student student;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    @JsonManagedReference
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="purpose_id", nullable=true)
    @JsonManagedReference
    private Purpose purpose;

    private Date applicationDate;


    @OneToMany(mappedBy="subjectApplication")
    @JsonBackReference
    private Set<SubjectApplicationFeedback> feedbacks;

    public SubjectApplication() {
    }

    public SubjectApplication(Long id, Student student, Subject subject, Purpose purpose, Date applicationDate) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.purpose = purpose;
        this.applicationDate = applicationDate;
    }

    public SubjectApplication(Long id, Student student, Subject subject, Purpose purpose, Date applicationDate, Set<SubjectApplicationFeedback> feedbacks) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.purpose = purpose;
        this.applicationDate = applicationDate;
        this.feedbacks = feedbacks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Set<SubjectApplicationFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<SubjectApplicationFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectApplication that = (SubjectApplication) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(subject, that.subject) && Objects.equals(purpose, that.purpose) && Objects.equals(applicationDate, that.applicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, subject, purpose, applicationDate);
    }

    @Override
    public String toString() {
        return "SubjectApplication{" +
                "id=" + id +
                ", student=" + student +
                ", subject=" + subject +
                ", purpose=" + purpose +
                ", applicationDate=" + applicationDate +
                '}';
    }
}
