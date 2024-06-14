package org.example.campusapp.service;

import org.example.campusapp.DTOs.UserDTO;
import org.example.campusapp.model.AppUser;
import org.example.campusapp.model.Student;
import org.example.campusapp.model.Teacher;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public AppUser map(UserDTO userDTO) {
        AppUser appUser;
        if (userDTO.getUserID().startsWith("t")) {
            appUser = new Teacher();
        } else if (userDTO.getUserID().startsWith("s")) {
            appUser = new Student();
        } else {
            throw new IllegalArgumentException("Invalid User ID prefix");
        }
        appUser.setId(userDTO.getId());
        appUser.setUserID(userDTO.getUserID());
        appUser.setEmail(userDTO.getEmail());
        appUser.setNotifications(userDTO.getNotifications());
        appUser.setPassword(userDTO.getPassword());
        appUser.setName(userDTO.getName());
        appUser.setSurname(userDTO.getSurname());
        return appUser;
    }

    public UserDTO map(AppUser appUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(appUser.getId());
        userDTO.setUserID(appUser.getUserID());
        userDTO.setEmail(appUser.getEmail());
        userDTO.setNotifications(appUser.getNotifications());
        userDTO.setPassword(appUser.getPassword());
        userDTO.setName(appUser.getName());
        userDTO.setSurname(appUser.getSurname());
        return userDTO;
    }
}
