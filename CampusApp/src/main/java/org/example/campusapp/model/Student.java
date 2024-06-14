package org.example.campusapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends AppUser {

    @ManyToOne
    @JoinColumn(name = "studentgroup_id")
    private StudentGroup studentGroup;

    public Student() {
    }


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentSubjectAttendance> attendances = new HashSet<>();

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Set<StudentSubjectAttendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<StudentSubjectAttendance> attendances) {
        this.attendances = attendances;
    }
}

