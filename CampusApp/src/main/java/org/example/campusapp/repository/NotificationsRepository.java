package org.example.campusapp.repository;

import org.aspectj.weaver.ast.Not;
import org.aspectj.weaver.patterns.NotTypePattern;
import org.example.campusapp.model.AppUser;
import org.example.campusapp.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByAppuser(AppUser appUser);
}
