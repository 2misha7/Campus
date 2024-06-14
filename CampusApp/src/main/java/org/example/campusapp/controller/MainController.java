package org.example.campusapp.controller;


import jakarta.validation.Valid;
import org.example.campusapp.DTOs.LoginDTO;
import org.example.campusapp.DTOs.UserDTO;
import org.example.campusapp.model.AppUser;
import org.example.campusapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDTO userDTO, Model model){
        userDTO.setNotifications(new ArrayList<>());
        if (userService.isApproved(userDTO.getUserID())) {
            AppUser registered = userService.saveUser(userDTO);
            if (registered != null) {
                model.addAttribute("userDTO", registered);
                return "redirect:/login";
            } else {
                model.addAttribute("message", "Error saving user");
            }
        } else {
            model.addAttribute("message", "Wrong user ID");
        }
        return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "login";
        }
        if(userService.loginSuccess(loginDTO).equals("teacher")){
            redirectAttributes.addAttribute("userID", loginDTO.getUserID());
            return "redirect:/teacher/homepage";
        }
        else if(userService.loginSuccess(loginDTO).equals("admin")){

            return "redirect:/admin/homepage";
        }
        else if(userService.loginSuccess(loginDTO).equals("student")){
            redirectAttributes.addAttribute("userID", loginDTO.getUserID());
            return "redirect:/student/homepage";
        }
        else{
            model.addAttribute("message", "Login failed");
        }
        return "login";
    }
}
