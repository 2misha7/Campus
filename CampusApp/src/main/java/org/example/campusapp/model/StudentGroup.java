package org.example.campusapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.example.campusapp.constraint.StartG;

import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3)
    private String name;

    @OneToMany(mappedBy = "studentGroup")
    private Set<Student> students = new HashSet<>();


    @OneToMany(mappedBy = "studentGroup")
    private Set<SubjectClass> subjectClasses = new HashSet<>();

    public StudentGroup() {
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
