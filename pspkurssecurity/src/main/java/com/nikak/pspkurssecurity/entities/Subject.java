package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String filename;


    @ManyToMany(mappedBy = "teacherSubjects")
    @JsonBackReference
    private Set<Teacher> teachers = new HashSet<>();
}
