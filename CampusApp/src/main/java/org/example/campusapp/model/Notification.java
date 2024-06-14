package org.example.campusapp.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appuser;

    private String description;
    private LocalDateTime dateTime;


    public Notification(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public AppUser getAppuser() {
        return appuser;
    }

    public void setAppuser(AppUser appuser) {
        this.appuser = appuser;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
