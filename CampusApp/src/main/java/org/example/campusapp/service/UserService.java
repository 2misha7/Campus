package org.example.campusapp.service;


import org.example.campusapp.DTOs.LoginDTO;
import org.example.campusapp.DTOs.UserDTO;
import org.example.campusapp.model.*;
import org.example.campusapp.repository.AdminRepository;
import org.example.campusapp.repository.ApprovedUserRepository;
import org.example.campusapp.repository.StudentRepository;
import org.example.campusapp.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final ApprovedUserRepository approvedUserRepository;

    public UserService(UserMapper userMapper, StudentRepository studentRepository, TeacherRepository teacherRepository, AdminRepository adminRepository, ApprovedUserRepository approvedUserRepository) {
        this.userMapper = userMapper;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.adminRepository = adminRepository;
        this.approvedUserRepository = approvedUserRepository;
    }

    public AppUser saveUser(UserDTO userDTO){
        AppUser appUser = userMapper.map(userDTO);

        if (userDTO.getUserID().startsWith("t")) {
            return teacherRepository.save((Teacher) appUser);
        } else if (userDTO.getUserID().startsWith("s")) {
            return studentRepository.save((Student) appUser);
        }
        return null;
    }

    public boolean isApproved(String userID){
        return approvedUserRepository.findByUserID(userID).isPresent();
    }





    public String loginSuccess(LoginDTO loginDTO) {
        try {
            Optional<Teacher> teacher = teacherRepository.findByUserID(loginDTO.getUserID());

            Optional<Student> student = studentRepository.findByUserID(loginDTO.getUserID());


            if (teacher.isPresent() && teacher.get().getPassword().equals(loginDTO.getPassword())) {

                return "teacher";
            } else if (student.isPresent() && student.get().getPassword().equals(loginDTO.getPassword())) {
                return "student";
            } else if (loginDTO.getUserID().equals("admin") && loginDTO.getPassword().equals("123qwertQWERT!!!")) {
                return "admin";
            } else {
                return "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
