package org.example.campusapp.controller;


import ch.qos.logback.core.net.server.ServerRunner;
import org.example.campusapp.model.*;
import org.example.campusapp.service.StudentService;
import org.example.campusapp.service.TeacherService;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public TeacherController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/homepage")
    public String teacherHomepage(@RequestParam("userID") String userID, Model model) {
        model.addAttribute("userID", userID);
        return "teacherHomePage";
    }

    @GetMapping("/allNotifications")
    public String allNotifications(@RequestParam(required = false) String userID, Model model) {
        if (userID != null) {
            Teacher teacher = teacherService.findById(userID);
            System.out.println(teacher);
            List<Notification> all = teacherService.allNotifications(teacher);
            model.addAttribute("all", all);
        }
        return "notifications";
    }

    @GetMapping("/allGroups")
    public String allGroups(@RequestParam(required = false) String userID, Model model){

        Teacher teacher = teacherService.findById(userID);


        if (teacher == null) {
            model.addAttribute("message", "Teacher not found.");
            return "allgroups";
        }

        // Retrieve all groups associated with the teacher
        Set<StudentGroup> groups = new HashSet<>();
        for (SubjectClass subjectClass : teacher.getClasses()) {
            groups.add(subjectClass.getStudentGroup());
        }

        model.addAttribute("groups", groups);
        model.addAttribute("userID", userID);
        return "allgroups";
    }


    @GetMapping("/viewSchedule")
    public String viewSchedule(@RequestParam(required = false) String userID, Model model) {
        Teacher teacher = teacherService.findById(userID);
        List<SubjectClass> subjectClasses = teacherService.getSubjectClassesByTeacher(teacher);
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
                data.add(subjectClass.getStudentGroup().getName());
                data.add(subjectClass.getStartTime().toString());
                data.add(subjectClass.getEndTime().toString());
                data.add("--------------------------------------------------");
            }
            model.addAttribute(key, data);
        }
        return "viewTeacherSchedule";
    }

    @GetMapping("/scheduleChange")
    public String scheduleChange(@RequestParam(required = false) String userID, Model model){
        model.addAttribute("userID", userID);
        return "scheduleChange";
    }
    @PostMapping("/scheduleChange")
    public String groupChange(@RequestParam(required = false) String userID, @RequestParam(required = false) String subject,@RequestParam(required = false) String group, @RequestParam(required = false) String day,@RequestParam(required = false) LocalTime time, Model model) {
        if(userID != null && day != null && time != null && subject != null && group != null){
            try{
                teacherService.newChangeRequest(group,  subject, day, time , userID);
                model.addAttribute("message", "Your request has been added to the system");
            }
            catch(Exception e){
                model.addAttribute("message", "Failed request");
            }
        }
        model.addAttribute("userID", userID);
        return "scheduleChange";
    }



    @GetMapping("/groupDetails")
    public String groupDetails(@RequestParam(required = false) String groupId, Model model){
        List<Student> students = teacherService.findStudentsByGroup(groupId);
        model.addAttribute("students",students);
        model.addAttribute("groupId", groupId);
        return "groupDetails";
    }

    @PostMapping("/groupDetails")
    public String groupDetails(@RequestParam(required = false) String groupId,
                               @RequestParam(required = false) String subjectName,
                               @RequestParam(required = false) List<String> present, Model model){
        Optional<StudentGroup> group = teacherService.findGroupByID(groupId);
        if(!group.isPresent()){
            model.addAttribute("message", "Group does not exist");
            return "groupDetails";
        }
        Optional<Subject> subject = teacherService.fingSubectByName(subjectName);
        if(!subject.isPresent()){
            model.addAttribute("message", "Wrong subject");
            return "groupDetails";
        }
        Optional<SubjectClass> subjectClass = teacherService.getSubjectClassByGroupSubject(group.get(), subject.get());
        if(!subjectClass.isPresent()){
            model.addAttribute("message", "This group does not have this subject");
            return "groupDetails";
        }
        List<Student> allStudents = teacherService.findStudentsByGroup(groupId);
        if(present == null){
            for(Student student:allStudents){


                        Optional<StudentSubjectAttendance> studentSubjectAttendance = teacherService.findSubjectStudentAttendance(String.valueOf(student.getId()), subject);
                        StudentSubjectAttendance st = studentSubjectAttendance.get();
                        st.setAbsent(st.getAbsent()+1);
                        teacherService.saveAttendance(st);

            }
            model.addAttribute("message","Attendance has been submitted");
            return "groupDetails";

        }

        for(Student student:allStudents){
            if(present.contains(student)){

                boolean has = teacherService.studentHasAttendanceForSubject(String.valueOf(student.getId()), subject);
                if(has){
                    Optional<StudentSubjectAttendance> studentSubjectAttendance = teacherService.findSubjectStudentAttendance(String.valueOf(student.getId()), subject);
                    StudentSubjectAttendance st = studentSubjectAttendance.get();
                    st.setAbsent(st.getAbsent()+1);
                    teacherService.saveAttendance(st);
                }else{
                    StudentSubjectAttendance st = new StudentSubjectAttendance();
                    st.setAbsent(1);
                    st.setPresent(0);
                    st.setStudent(studentService.findByIdId(String.valueOf(student.getId())));
                    st.setSubject(subject.get());
                    teacherService.saveAttendance(st);
                }
            }
        }
        for(String studentID: present){
            boolean has = teacherService.studentHasAttendanceForSubject(studentID, subject);
            if(has){
                Optional<StudentSubjectAttendance> studentSubjectAttendance = teacherService.findSubjectStudentAttendance(studentID, subject);
                StudentSubjectAttendance st = studentSubjectAttendance.get();
                st.setPresent(st.getPresent()+1);
                teacherService.saveAttendance(st);
            }else{
                StudentSubjectAttendance st = new StudentSubjectAttendance();
                st.setAbsent(0);
                st.setPresent(1);
                System.out.println(studentID);
                st.setStudent(studentService.findByIdId(studentID));
                st.setSubject(subject.get());
                teacherService.saveAttendance(st);
            }
        }

        model.addAttribute("message","Attendance has been submitted");
        return "groupDetails";
    }

}
