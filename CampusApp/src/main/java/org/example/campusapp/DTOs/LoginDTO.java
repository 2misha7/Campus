package org.example.campusapp.DTOs;

import jakarta.validation.constraints.Size;
import org.example.campusapp.constraint.*;

public class LoginDTO {
    @IdStart
    @Size(min = 4)
    private String userID;

    @PassswordWithLowercase
    @PasswordDigits
    @PasswordLength
    @PasswordWithUppercase
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
