package org.example.campusapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Teacher extends AppUser {
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectClass> classes = new HashSet<>();

    public Teacher() {

    }

    public Set<SubjectClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SubjectClass> classes) {
        this.classes = classes;
    }
}
