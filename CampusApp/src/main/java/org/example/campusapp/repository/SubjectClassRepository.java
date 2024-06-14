package org.example.campusapp.repository;

import org.example.campusapp.model.*;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SubjectClassRepository extends CrudRepository<SubjectClass, Long> {
    boolean existsByStudentGroupAndDayAndStartTime(StudentGroup group, String day, LocalTime startTime);
    List<SubjectClass>  findAllByStudentGroup(StudentGroup studentGroup);
    List<SubjectClass> findAllByTeacher(Teacher teacher);
    Optional<SubjectClass> findByStudentGroupAndSubject(StudentGroup studentGroup, Subject subject);
}
