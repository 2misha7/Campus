package org.example.campusapp.repository;

import org.example.campusapp.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Optional<Teacher> findByUserID(String userID);
}
