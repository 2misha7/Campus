package org.example.campusapp.service;


import org.example.campusapp.model.*;
import org.example.campusapp.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminNotificationRepository adminNotificationRepository;
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TeacherRepository teacherRepository;
    private final ChangeRequestRepository changeRequestRepository;
    private final StudentSubjectAttendanceRepository studentSubjectAttendanceRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectClassRepository subjectClassRepository;
    private final ApprovedUserRepository approvedUserRepository;
    public AdminService(AdminNotificationRepository adminNotificationRepository, StudentRepository studentRepository, StudentGroupRepository studentGroupRepository, TeacherRepository teacherRepository, ChangeRequestRepository changeRequestRepository, StudentSubjectAttendanceRepository studentSubjectAttendanceRepository, SubjectRepository subjectRepository, SubjectClassRepository subjectClassRepository, ApprovedUserRepository approvedUserRepository) {
        this.adminNotificationRepository = adminNotificationRepository;
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.teacherRepository = teacherRepository;
        this.changeRequestRepository = changeRequestRepository;
        this.studentSubjectAttendanceRepository = studentSubjectAttendanceRepository;
        this.subjectRepository = subjectRepository;
        this.subjectClassRepository = subjectClassRepository;
        this.approvedUserRepository = approvedUserRepository;
    }




    public List<AdminNotification> allNotifications() {
        List<AdminNotification> all = adminNotificationRepository.findAllBy();
        return all;
    }

    public String assignStudentToGroup(String userID, String groupName) {
        try {
            Optional<Student> studentOpt = studentRepository.findByUserID(userID);
            if (!studentOpt.isPresent()) {
                return "User ID does not exist";
            }

            Optional<StudentGroup> studentGroupOpt = studentGroupRepository.findByName(groupName);
            if (!studentGroupOpt.isPresent()) {
                return "Group does not exist";
            }

            Student student = studentOpt.get();
            StudentGroup studentGroup = studentGroupOpt.get();

            if (studentGroup.getStudents().size() >= 20) {
                return "Group has already reached the maximum number of students";
            }

            if (student.getStudentGroup() != null) {
                StudentGroup currentGroup = student.getStudentGroup();
                currentGroup.getStudents().remove(student);
                studentGroupRepository.save(currentGroup);
            }

            student.setStudentGroup(studentGroup);
            studentGroup.getStudents().add(student);

            try {
                studentRepository.save(student);
                studentGroupRepository.save(studentGroup);
            } catch (Exception e) {

                e.printStackTrace();
                return "Failed to assign student to the group: " + e.getMessage();
            }

            return "Student successfully assigned to the group";
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    public Optional<StudentGroup> findGroupByName(String group) {
        return studentGroupRepository.findByName(group);
    }

    public Optional<Subject> findSubjectByName(String subject) {
        return subjectRepository.findByName(subject);
    }

    public Optional<Teacher> findTeacherById(String tutorID) {
        return teacherRepository.findByUserID(tutorID);
    }

    public boolean checkSubjectClassConflict(StudentGroup group, String day, LocalTime startTime) {
        return subjectClassRepository.existsByStudentGroupAndDayAndStartTime(group, day, startTime);
    }

    public void saveSubjectClass(SubjectClass subjectClass) {
        subjectClassRepository.save(subjectClass);
    }

    public boolean addedApproved(String userID){
        return approvedUserRepository.findByUserID(userID).isPresent();
    }

    public void addNewApprovedUser(String userID){
        ApprovedUser approvedUser = new ApprovedUser();
        approvedUser.setUserID(userID);
        approvedUserRepository.save(approvedUser);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        return subjects;
    }

    public List<StudentSubjectAttendance> findSubjectAttendances(Subject subject) {
        List<StudentSubjectAttendance> studentSubjectAttendances = studentSubjectAttendanceRepository.findBySubject(subject);
        return studentSubjectAttendances;
    }

    public ApprovedUser saveApprovedUser(String userID) {
        ApprovedUser user = new ApprovedUser();
        user.setUserID(userID);
        return approvedUserRepository.save(user);
    }

    public List<ChangeRequest> allChangeRequests() {
        return (List<ChangeRequest>) changeRequestRepository.findAll();
    }

    public String deleteUser(String userID) {
       if(userID.startsWith("s")){
           Student student = studentRepository.findByUserID(userID).get();
           studentRepository.delete(student);
           return "This student has been deleted successfully";
       }else if(userID.startsWith("t")){
           Teacher teacher = teacherRepository.findByUserID(userID).get();
           teacherRepository.delete(teacher);
           return "This teacher has been deleted successfully";
       }else{
           return "WRONG USER ID";
       }
    }

    public Optional<Student> findUserById(String userID) {
        return studentRepository.findByUserID(userID);
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public void saveGroup(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }
}

