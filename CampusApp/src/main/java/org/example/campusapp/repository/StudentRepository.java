package org.example.campusapp.repository;

import org.example.campusapp.model.Student;
import org.example.campusapp.model.StudentGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByUserID(String userID);
    List<Student> findAllByStudentGroup(StudentGroup studentGroup);
}
