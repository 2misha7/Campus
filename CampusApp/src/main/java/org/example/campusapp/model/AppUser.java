package org.example.campusapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.example.campusapp.constraint.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appuser")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "appuser")
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "appuser")
    private List<ChangeRequest> changeRequests = new ArrayList<>();

    @IdStart
    @Size(min = 4)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<ChangeRequest> getChangeRequests() {
        return changeRequests;
    }

    public void setChangeRequests(List<ChangeRequest> changeRequests) {
        this.changeRequests = changeRequests;
    }
}
