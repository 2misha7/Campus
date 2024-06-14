package org.example.campusapp.repository;

import org.example.campusapp.model.ApprovedUser;
import org.springframework.data.repository.CrudRepository;
import org.w3c.dom.css.CSSRule;

import java.util.Optional;

public interface ApprovedUserRepository extends CrudRepository<ApprovedUser, Long> {
    Optional<ApprovedUser> findByUserID(String userID);
}
