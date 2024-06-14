package org.example.campusapp.repository;

import org.example.campusapp.model.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Optional<Admin> findByUserID(String userID);
}
