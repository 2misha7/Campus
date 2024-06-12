package org.example.campusapp.repository;

import org.example.campusapp.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRrpository extends CrudRepository<Student, Long> {
    Optional<Student> findByUserID(String userID);
}
