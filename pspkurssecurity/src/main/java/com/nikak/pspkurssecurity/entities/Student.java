package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="student")
    @JsonIgnore
    private Set<Rating> teacherRating;

    public Student() {
    }


    public Student(Long id, String name, User user, Set<Rating> teacherRating) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.teacherRating = teacherRating;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Rating> getTeacherRating() {
        return teacherRating;
    }

    public void setTeacherRating(Set<Rating> teacherRating) {
        this.teacherRating = teacherRating;
    }
}
