package org.example.campusapp.repository;

import org.example.campusapp.model.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {
    Optional<UserGroup> findByName(String name);
}
