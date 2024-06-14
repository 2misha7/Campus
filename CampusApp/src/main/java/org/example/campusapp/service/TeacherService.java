package org.example.campusapp.service;

import org.example.campusapp.model.*;
import org.example.campusapp.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final NotificationsRepository notificationsRepository;
    private final SubjectClassRepository subjectClassRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ChangeRequestRepository changeRequestRepository;
    private final StudentSubjectAttendanceRepository studentSubjectAttendanceRepository;
    public TeacherService(TeacherRepository teacherRepository, NotificationsRepository notificationsRepository, SubjectClassRepository subjectClassRepository, StudentGroupRepository studentGroupRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, ChangeRequestRepository changeRequestRepository, StudentSubjectAttendanceRepository studentSubjectAttendanceRepository) {
        this.teacherRepository = teacherRepository;
        this.notificationsRepository = notificationsRepository;
        this.subjectClassRepository = subjectClassRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.changeRequestRepository = changeRequestRepository;
        this.studentSubjectAttendanceRepository = studentSubjectAttendanceRepository;
    }


    public Teacher findById(String userID) {
        return teacherRepository.findByUserID(userID).get();
    }

    public List<Notification> allNotifications(Teacher teacher) {
        List<Notification> all = notificationsRepository.findAllByAppuser(teacher);
        return all;
    }

    public List<SubjectClass> getSubjectClassesByTeacher(Teacher teacher) {
        return subjectClassRepository.findAllByTeacher(teacher);
    }

    public List<Student> findStudentsByGroup(String groupId) {
        Optional<StudentGroup> group = studentGroupRepository.findById(Long.valueOf(groupId));
        System.out.println(group.get().getName());
        List<Student> students = studentRepository.findAllByStudentGroup(group.get());
        return students;
    }

    public Optional<StudentGroup> findGroupByID(String groupId) {
        return studentGroupRepository.findById(Long.valueOf(groupId));
    }

    public Optional<Subject> fingSubectByName(String subjectName) {
        return subjectRepository.findByName(subjectName);
    }

    public Optional<SubjectClass> getSubjectClassByGroupSubject(StudentGroup group, Subject subject) {
        return subjectClassRepository.findByStudentGroupAndSubject(group,subject);
    }

    public boolean studentHasAttendanceForSubject(String studentID, Optional<Subject> subject) {
        Optional<Student> student = studentRepository.findById(Long.valueOf(studentID));
        Optional<StudentSubjectAttendance> studentSubjectAttendance = studentSubjectAttendanceRepository.findByStudentAndSubject(student.get(),subject.get());
        if(studentSubjectAttendance.isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public Optional<StudentSubjectAttendance> findSubjectStudentAttendance(String studentID, Optional<Subject> subject) {
        Optional<Student> student = studentRepository.findById(Long.valueOf(studentID));
        Optional<StudentSubjectAttendance> studentSubjectAttendance = studentSubjectAttendanceRepository.findByStudentAndSubject(student.get(),subject.get());
        return studentSubjectAttendance;
    }

    public void saveAttendance(StudentSubjectAttendance st) {
        studentSubjectAttendanceRepository.save(st);
    }

    public void newChangeRequest(String group, String subject, String day, LocalTime time, String userID) {
        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setAppuser(teacherRepository.findByUserID(userID).get());
        changeRequest.setDescription("User " + userID + "requests to change the time of a class: Subject - " + subject + "; Group - " + group + ". Desired date and time - " + day + " " + time );
        changeRequest.setDateTime(LocalDateTime.now());
        changeRequestRepository.save(changeRequest);
    }
}
