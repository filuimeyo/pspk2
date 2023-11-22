package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
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
    @JsonBackReference
    private Set<Subject> teacherSubjects = new HashSet<>();


    public void setFinalRating() {
        this.finalRating = 0.0;
//                (double) teacherRating.size();
//                .stream().map(Rating::getRating).map(Double::valueOf)
//                .reduce(Double::sum).map(it->it/teacherRating.size())
//                .orElse(0.0);
    }


    public Double getFinalRating() {
        return  0.0;/*teacherRating.stream().map(Rating::getRating).map(Double::valueOf)
                .reduce(Double::sum).map(it->it/teacherRating.size())
                .orElse(0.0);*/
    }

}
