package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Purpose {
    @SequenceGenerator(
            name = "purpose_sequence",
            sequenceName = "purpose_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "purpose_sequence"
    )
    private Long id;

    private String purpose;

    @ManyToMany(mappedBy = "purposes")
    @JsonBackReference
    private Set<Teacher> teachers = new HashSet<>();

    public Purpose() {
    }

    public Purpose(String purpose) {
        this.purpose = purpose;
    }

    public Purpose(Long id, String purpose, Set<Teacher> teachers) {
        this.id = id;
        this.purpose = purpose;
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
