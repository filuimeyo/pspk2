package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static java.util.Arrays.stream;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String info;
    private double lessonPrice;

    private String filename;

    @OneToMany(mappedBy="teacher")
    @JsonManagedReference
    private Set<Rating> teacherRating;

    @OneToMany(mappedBy="teacher")
    @JsonBackReference
    private Set<TeacherApplication> teacherApplications;

    @Transient
    private Double finalRating;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_subject",
            joinColumns =  @JoinColumn(name = "teacher_id") ,
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonManagedReference
    private Set<Subject> teacherSubjects = new HashSet<>();



    @OneToMany(mappedBy="teacher")
    @JsonManagedReference
    private Set<Certificate> certificates;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_purpose",
            joinColumns =  @JoinColumn(name = "teacher_id") ,
            inverseJoinColumns = @JoinColumn(name = "purpose_id")
    )
    @JsonManagedReference
    private Set<Purpose> purposes = new HashSet<>();

    @OneToMany(mappedBy="teacher")
    @JsonBackReference
    private Set<SubjectApplicationFeedback> feedbacks;

    public Teacher() {
    }

    public Teacher(Long id, String name, String info, double lessonPrice, String filename, Set<Rating> teacherRating, Set<TeacherApplication> teacherApplications, Double finalRating, User user, Set<Subject> teacherSubjects, Set<Certificate> certificates, Set<Purpose> purposes, Set<SubjectApplicationFeedback> feedbacks) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.lessonPrice = lessonPrice;
        this.filename = filename;
        this.teacherRating = teacherRating;
        this.teacherApplications = teacherApplications;
        this.finalRating = finalRating;
        this.user = user;
        this.teacherSubjects = teacherSubjects;
        this.certificates = certificates;
        this.purposes = purposes;
        this.feedbacks = feedbacks;
    }

    public Teacher(Long id, String name, String info, double lessonPrice, String filename, Set<Rating> teacherRating, Set<TeacherApplication> teacherApplications, Double finalRating, User user, Set<Subject> teacherSubjects, Set<Certificate> certificates, Set<Purpose> purposes) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.lessonPrice = lessonPrice;
        this.filename = filename;
        this.teacherRating = teacherRating;
        this.teacherApplications = teacherApplications;
        this.finalRating = finalRating;
        this.user = user;
        this.teacherSubjects = teacherSubjects;
        this.certificates = certificates;
        this.purposes = purposes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getLessonPrice() {
        return lessonPrice;
    }

    public void setLessonPrice(double lessonPrice) {
        this.lessonPrice = lessonPrice;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<Rating> getTeacherRating() {
        return teacherRating;
    }

    public void setTeacherRating(Set<Rating> teacherRating) {
        this.teacherRating = teacherRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Subject> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(Set<Subject> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    public void setFinalRating() {
        this.finalRating = 0.0;
    }


    public Double getFinalRating() {
        return  teacherRating.stream().map(Rating::getRating).map(Double::valueOf)
                .reduce(Double::sum).map(it->it/teacherRating.size())
                .orElse(0.0);
    }

    public Set<TeacherApplication> getTeacherApplications() {
        return teacherApplications;
    }

    public void setTeacherApplications(Set<TeacherApplication> teacherApplications) {
        this.teacherApplications = teacherApplications;
    }

    public void setFinalRating(Double finalRating) {
        this.finalRating = finalRating;
    }


    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public Set<Purpose> getPurposes() {
        return purposes;
    }

    public void setPurposes(Set<Purpose> purposes) {
        this.purposes = purposes;
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
        Teacher teacher = (Teacher) o;
        return Double.compare(lessonPrice, teacher.lessonPrice) == 0 && Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(info, teacher.info) && Objects.equals(filename, teacher.filename) && Objects.equals(finalRating, teacher.finalRating) && Objects.equals(user, teacher.user) && Objects.equals(teacherSubjects, teacher.teacherSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, info, lessonPrice, filename, finalRating, user, teacherSubjects);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", lessonPrice=" + lessonPrice +
                ", filename='" + filename + '\'' +
                ", teacherRating=" + teacherRating +
                ", teacherApplications=" + teacherApplications +
                ", finalRating=" + finalRating +
                ", user=" + user +
                ", teacherSubjects=" + teacherSubjects +
                ", certificates=" + certificates +
                ", purposes=" + purposes +
                '}';
    }
}
