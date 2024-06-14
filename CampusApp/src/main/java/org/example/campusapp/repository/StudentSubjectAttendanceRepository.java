package org.example.campusapp.repository;


import org.example.campusapp.model.Student;
import org.example.campusapp.model.StudentSubjectAttendance;
import org.example.campusapp.model.Subject;
import org.example.campusapp.model.SubjectClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSubjectAttendanceRepository extends CrudRepository<StudentSubjectAttendance, Long> {
    Optional<StudentSubjectAttendance> findByStudentAndSubject(Student student, Subject subject);
    List<StudentSubjectAttendance> findBySubject(Subject subject);
}
