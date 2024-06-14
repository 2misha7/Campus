package org.example.campusapp.repository;

import org.example.campusapp.model.Admin;
import org.example.campusapp.model.StudentGroup;
import org.example.campusapp.model.SubjectClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<StudentGroup, Long> {
    Optional<StudentGroup> findByName(String name);

}
