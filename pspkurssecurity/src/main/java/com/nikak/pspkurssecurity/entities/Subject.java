package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
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

    @OneToMany(mappedBy="subject")
    @JsonBackReference
    private Set<TeacherApplication> teacherApplications;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name) && Objects.equals(filename, subject.filename) && Objects.equals(teachers, subject.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, filename, teachers);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
