package org.example.campusapp.repository;

import org.example.campusapp.model.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Optional<Subject> findByName(String subject);
}
