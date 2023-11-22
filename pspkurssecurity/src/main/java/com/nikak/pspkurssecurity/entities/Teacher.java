package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    @JsonManagedReference
    private List<Subject> teacherSubjects = new ArrayList<>();


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
                ", finalRating=" + finalRating +
                ", teacherSubjects=" + teacherSubjects +
                '}';
    }
}
