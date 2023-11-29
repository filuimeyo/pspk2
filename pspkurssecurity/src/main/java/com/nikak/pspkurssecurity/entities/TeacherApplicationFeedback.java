package com.nikak.pspkurssecurity.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class TeacherApplicationFeedback extends Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id")
    private TeacherApplication application;

    private Date applicationDate;

    public TeacherApplicationFeedback() {
    }

    public TeacherApplicationFeedback(FeedbackType feedbackType, String email, TeacherApplication application) {
        this.feedbackType = feedbackType;
        this.email = email;
        this.application = application;
    }

    public TeacherApplicationFeedback(Long id, FeedbackType feedbackType, String email, TeacherApplication application) {
        this.id = id;
        this.feedbackType = feedbackType;
        this.email = email;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TeacherApplication getApplication() {
        return application;
    }

    public void setApplication(TeacherApplication application) {
        this.application = application;
    }

    @Override
    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
}
