package com.nikak.pspkurssecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;


@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String filename;



    @ManyToMany(mappedBy = "teacherSubjects")
    @JsonBackReference
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy="subject")
    @JsonBackReference
    private Set<TeacherApplication> teacherApplications;

    @OneToMany(mappedBy="subject")
    @JsonBackReference
    private Set<SubjectApplication> subjectApplications;

    public Subject() {
    }

    public Subject(Long id, String name, String filename, Set<TeacherApplication> teacherApplications) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.teacherApplications = teacherApplications;
    }

    public Subject(Long id, String name, String filename, List<Teacher> teachers, Set<TeacherApplication> teacherApplications, Set<SubjectApplication> subjectApplications) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.teachers = teachers;
        this.teacherApplications = teacherApplications;
        this.subjectApplications = subjectApplications;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<TeacherApplication> getTeacherApplications() {
        return teacherApplications;
    }

    public void setTeacherApplications(Set<TeacherApplication> teacherApplications) {
        this.teacherApplications = teacherApplications;
    }

    public Set<SubjectApplication> getSubjectApplications() {
        return subjectApplications;
    }

    public void setSubjectApplications(Set<SubjectApplication> subjectApplications) {
        this.subjectApplications = subjectApplications;
    }

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
                ", teachers=" + teachers +
                ", teacherApplications=" + teacherApplications +
                '}';
    }
}
