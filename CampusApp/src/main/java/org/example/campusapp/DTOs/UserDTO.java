package org.example.campusapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import org.example.campusapp.constraint.*;
import org.example.campusapp.model.Notification;

import java.util.List;

public class UserDTO {
    @JsonIgnore
    private Long id;
    private String userID;
    private String name;
    private String surname;
    @Email
    private String email;
    @PassswordWithLowercase
    @PasswordDigits
    @PasswordLength
    @PasswordWithUppercase
    private String password;
    private List<Notification> notifications;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
