package org.example.campusapp.controller;
import org.example.campusapp.model.*;
import org.example.campusapp.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/homepage")
    public String teacherHomepage(Model model) {
        return "adminHomePage";
    }

    @GetMapping("/allNotifications")
    public String allNotifications(Model model) {
        List<AdminNotification> all = adminService.allNotifications();
        model.addAttribute("all", all);
        return "notifications";
    }


    @GetMapping("/createSchedule")
    public String createSchedule(Model model) {
        return "createSchedule";
    }

    @PostMapping("/createSchedule")
    public String createSchedule(
            @RequestParam String group,
            @RequestParam String subject,
            @RequestParam String tutorID,
            @RequestParam String day,
            @RequestParam LocalTime startTime, Model model) {

        Optional<StudentGroup> optionalGroup = adminService.findGroupByName(group);
        Optional<Subject> optionalSubject = adminService.findSubjectByName(subject);
        Optional<Teacher> optionalTeacher = adminService.findTeacherById(tutorID);
        if (optionalGroup.isEmpty()) {
            model.addAttribute("message", "Group does not exist.");
            return "createSchedule";
        }

        if (optionalSubject.isEmpty()) {
            model.addAttribute("message", "Subject does not exist.");
            return "createSchedule";
        }

        if (optionalTeacher.isEmpty()) {
            model.addAttribute("message", "Teacher does not exist.");
            return "createSchedule";
        }

        StudentGroup groupEntity = optionalGroup.get();
        Subject subjectEntity = optionalSubject.get();
        Teacher teacherEntity = optionalTeacher.get();

        boolean conflictExists = adminService.checkSubjectClassConflict(groupEntity, day, startTime);
        if (conflictExists) {
            model.addAttribute("message", "There's already a class scheduled for this group and time.");
            return "createSchedule";
        }

        LocalTime endTime = startTime.plusHours(1).plusMinutes(30);
        SubjectClass subjectClass = new SubjectClass();
        subjectClass.setDay(day);
        subjectClass.setStartTime(startTime);
        subjectClass.setEndTime(endTime);
        subjectClass.setTeacher(teacherEntity);
        subjectClass.setSubject(subjectEntity);
        subjectClass.setStudentGroup(groupEntity);


        adminService.saveSubjectClass(subjectClass);

        model.addAttribute("message", "Subject class created successfully.");
        return "createSchedule";
    }

    @GetMapping("/assignStudentToGroup")
    public String assignStudentToGroup(Model model) {
        return "assignStudentToGroup";
    }

    @PostMapping("/assignStudentToGroup")
    public String assignStudentToGroup(@RequestParam String userID, @RequestParam String groupName, Model model) {
        String message = adminService.assignStudentToGroup(userID, groupName);
        model.addAttribute("message", message);
        return "assignStudentToGroup";
    }


    @GetMapping("/viewStatistics")
    public String viewStatistics(Model model) {
        List<Subject> allSubjects = adminService.getAllSubjects();

        Map<String, List<Double>> forStatistics = new HashMap<>();
        double absences = 0;
        double presences = 0;
        for (Subject subject : allSubjects) {
            List<StudentSubjectAttendance> studentSubjectAttendances = adminService.findSubjectAttendances(subject);
            for (StudentSubjectAttendance studentSubjectAttendance : studentSubjectAttendances) {
                absences += studentSubjectAttendance.getAbsent();
                presences += studentSubjectAttendance.getPresent();
            }
            List<Double> data = new ArrayList<>();
            data.add(absences / 100);
            data.add(presences / 100);
            forStatistics.put(subject.getName(), data);
            absences = 0;
            presences = 0;
        }
        model.addAttribute("statistics", forStatistics);
        return "viewStatistics";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        return "addNewUser";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String userID, Model model) {
        if (adminService.addedApproved(userID)) {
            model.addAttribute("message", "User has already been added to the system");
        } else {
            adminService.addNewApprovedUser(userID);
            model.addAttribute("message", "User has been added successfully");
        }


        return "addNewUser";
    }


}




