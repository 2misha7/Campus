package org.example.campusapp.service;


import org.example.campusapp.model.*;
import org.example.campusapp.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final NotificationsRepository notificationsRepository;
    private final ChangeRequestRepository changeRequestRepository;
    private final SubjectClassRepository subjectClassRepository;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository, NotificationsRepository notificationsRepository, ChangeRequestRepository changeRequestRepository, SubjectClassRepository subjectClassRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.notificationsRepository = notificationsRepository;
        this.changeRequestRepository = changeRequestRepository;
        this.subjectClassRepository = subjectClassRepository;
    }

    public Student findById(String userID) {
        return studentRepository.findByUserID(userID).get();
    }
    public Student findByIdId(String userID) {
        return studentRepository.findById(Long.valueOf(userID)).get();
    }

    public List<Notification> allNotifications(Student student) {
        List<Notification> all = notificationsRepository.findAllByAppuser(student);
        return all;
    }

    public boolean groupExists(String desiredGroup) {
        return groupRepository.findByName(desiredGroup).isPresent();
    }

    public void newGroupChangeRequest(String currentGroup, String desiredGroup, String userID) {
        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setAppuser(studentRepository.findByUserID(userID).get());
        changeRequest.setDescription("User " + userID + ". Current group: " + currentGroup + ". Desired group: " + desiredGroup);
        changeRequest.setDateTime(LocalDateTime.now());
        changeRequestRepository.save(changeRequest);
    }


    public List<SubjectClass> getSubjectClassesByGroup(StudentGroup group){
        return subjectClassRepository.findAllByStudentGroup(group);
    }

}
