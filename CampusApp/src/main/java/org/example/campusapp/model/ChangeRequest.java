package org.example.campusapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    private AppUser appuser;
    private String description;
    private LocalDateTime dateTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppUser getAppuser() {
        return appuser;
    }

    public void setAppuser(AppUser appuser) {
        this.appuser = appuser;
    }
}
