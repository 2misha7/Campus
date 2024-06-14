package org.example.campusapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3)
    private String name;
    private int max_absences;
    private int numOfClasses;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentSubjectAttendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectClass> classes = new HashSet<>();
    public Subject(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SubjectClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SubjectClass> classes) {
        this.classes = classes;
    }

    public int getNumOfClasses() {
        return numOfClasses;
    }

    public void setNumOfClasses(int numOfClasses) {
        this.numOfClasses = numOfClasses;
    }

    public int getMax_absences() {
        return max_absences;
    }

    public void setMax_absences(int max_absences) {
        this.max_absences = max_absences;
    }
}
