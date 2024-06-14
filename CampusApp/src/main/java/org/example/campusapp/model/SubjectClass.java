package org.example.campusapp.model;

import jakarta.persistence.*;
import org.example.campusapp.constraint.Day;
import org.example.campusapp.repository.StudentGroupRepository;
import org.example.campusapp.repository.SubjectRepository;
import org.example.campusapp.repository.TeacherRepository;

import java.time.LocalTime;

@Entity
public class SubjectClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Day
    @Column(name = "\"day\"")
    private String day;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }
    public Teacher getTeacher(){
        return teacher;
    }

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "studentgroup_id")
    private StudentGroup studentGroup;

    public void setStudentGroup(StudentGroup studentGroup){
        this.studentGroup = studentGroup;
    }
    public StudentGroup getStudentGroup(){
        return studentGroup;
    }
    public SubjectClass() {

    }
    public void setSubject(Subject subject){
        this.subject = subject;
    }
    public Subject getSubject(){
        return subject;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}


