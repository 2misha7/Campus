package org.example.campusapp.repository;

import org.example.campusapp.model.AdminNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminNotificationRepository extends CrudRepository<AdminNotification, Long> {
    List<AdminNotification> findAllBy();
}
