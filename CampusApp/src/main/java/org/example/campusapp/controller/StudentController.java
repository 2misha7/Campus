package org.example.campusapp.controller;


import org.aspectj.bridge.IMessage;
import org.example.campusapp.model.*;
import org.example.campusapp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/homepage")
    public String teacherHomepage(@RequestParam("userID") String userID, Model model) {
        model.addAttribute("userID", userID);
        return "studentHomePage";
    }

    @GetMapping("/allNotifications")
    public String allNotifications(@RequestParam(required = false) String userID, Model model) {
        if (userID != null) {
            Student student = studentService.findById(userID);
            System.out.println(student);
            List<Notification> all = studentService.allNotifications(student);
            model.addAttribute("all", all);
        }
        return "notifications";
    }

    @GetMapping("/groupChange")
    public String showGroupChange(@RequestParam(required = false) String userID, Model model){
        model.addAttribute("userID", userID);
        return "groupChange";
    }

    @PostMapping("/groupChange")
    public String groupChange(@RequestParam(required = false) String userID, @RequestParam(required = false) String group, Model model) {
        if(userID != null && group != null){
            try{
                String currentGroup = "";
                if(studentService.findById(userID).getStudentGroup() == null){
                    currentGroup = "no group assigned";
                }else{
                    currentGroup = studentService.findById(userID).getStudentGroup().getName();
                }
                System.out.println(currentGroup);
                String desiredGroup = group;
                if(studentService.groupExists(desiredGroup)){
                    studentService.newGroupChangeRequest(currentGroup, desiredGroup, userID);
                    model.addAttribute("message", "Your request has been added to the system");
                }else{
                    model.addAttribute("message", "Desired group does not exist");
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        model.addAttribute("userID", userID);
        return "groupChange";
    }

    @GetMapping("/viewSchedule")
    public String viewSchedule(@RequestParam(required = false) String userID, Model model) {

        Student st = studentService.findById(userID);

        StudentGroup studentGroup = st.getStudentGroup();

        if (studentGroup == null) {
            model.addAttribute("message", "User does not have an associated group.");
            return "viewStudentSchedule";
        }

        List<SubjectClass> subjectClasses = studentService.getSubjectClassesByGroup(studentGroup);



        Map<String, List<SubjectClass>> scheduleMap = new LinkedHashMap<>();


        for (DayOfWeek day : DayOfWeek.values()) {
            String name = day.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            name.toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            scheduleMap.put(name, new ArrayList<>());
        }


        for (SubjectClass subjectClass : subjectClasses) {
            String day = subjectClass.getDay(); // Assuming day is stored as a string
            scheduleMap.get(day).add(subjectClass);
        }


        for (List<SubjectClass> classesOfDay : scheduleMap.values()) {
            classesOfDay.sort(Comparator.comparing(SubjectClass::getStartTime));
        }

        for (Map.Entry<String, List<SubjectClass>> entry : scheduleMap.entrySet()) {
            String key = entry.getKey();
            List<SubjectClass> classes = entry.getValue();
            List<String> data = new ArrayList<>();
            for(SubjectClass subjectClass: classes){
                data.add("--------------------------------------------------");
                data.add(subjectClass.getSubject().getName());
                data.add(subjectClass.getTeacher().getName());
                data.add(subjectClass.getTeacher().getSurname());
                data.add(subjectClass.getStartTime().toString());
                data.add(subjectClass.getEndTime().toString());
                data.add("--------------------------------------------------");
            }
            model.addAttribute(key, data);
        }


        return "viewStudentSchedule";
    }

}
