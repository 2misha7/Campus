package org.example.campusapp.DTOs;

import jakarta.validation.constraints.Size;
import org.example.campusapp.constraint.IdStart;

public class ApprovedUserDTO {

    @IdStart
    @Size(min = 4)
    private String userID;

    public ApprovedUserDTO() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
