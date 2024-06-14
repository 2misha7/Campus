package org.example.campusapp.repository;

import org.example.campusapp.model.StudentGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentGroupRepository extends CrudRepository<StudentGroup, Long> {
    Optional<StudentGroup> findByName(String name);
}
