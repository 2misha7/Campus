package org.example.campusapp.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.example.campusapp.DTOs.AllChangeRequestDTO;
import org.example.campusapp.DTOs.ApprovedUserDTO;
import org.example.campusapp.DTOs.UserDTO;
import org.example.campusapp.constraint.PassswordWithLowercase;
import org.example.campusapp.constraint.PasswordDigits;
import org.example.campusapp.constraint.PasswordLength;
import org.example.campusapp.constraint.PasswordWithUppercase;
import org.example.campusapp.model.*;
import org.example.campusapp.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("campus")
public class ApiController {
    private final AdminService adminService;

    public ApiController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Tag(name = "POST", description = "Add new approved user to the system")
    @PostMapping("approveduser")
    public ResponseEntity<?> saveUrl(@Valid @RequestBody ApprovedUserDTO approvedUserDTO) {
        ApprovedUser user = adminService.saveApprovedUser(approvedUserDTO.getUserID());
        return ResponseEntity.ok("Approved user ");
    }

    @Tag(name = "GET", description = "Get all change requests")
    @GetMapping("requests")
    public ResponseEntity<AllChangeRequestDTO> getLinkInfo() {
        AllChangeRequestDTO allChangeRequestDTO = new AllChangeRequestDTO();
        List<ChangeRequest> changeRequests = adminService.allChangeRequests();
        List<String> requestsStrings = new ArrayList<>();
        for(ChangeRequest changeRequest : changeRequests){
            requestsStrings.add(changeRequest.getDescription());
        }
        allChangeRequestDTO.setAllRequests(requestsStrings);
        return ResponseEntity.ok(allChangeRequestDTO);
    }

    @Tag(name = "DELETE", description = "Remove user by user ID")
    @DeleteMapping("appuser/{userID}")
    ResponseEntity<?> deleteUrl(@PathVariable String userID) {
        String response = adminService.deleteUser(userID);
        return ResponseEntity.ok(response);

    }



    @Tag(name = "PATCH", description = "Assign student to a new group")
    @PatchMapping("student/{userID}")
    public ResponseEntity<?> updateStudentGroup(@PathVariable String userID, @RequestParam String groupName) {
        Optional<Student> student = adminService.findUserById(userID);
        if(!student.isPresent()){
            return ResponseEntity.badRequest().body("\"No student with this id\"");
        }
        Optional<StudentGroup> group = adminService.findGroupByName(groupName);
        if(!group.isPresent()){
            return ResponseEntity.badRequest().body("\"No group with this name\"");
        }
        student.get().setStudentGroup(group.get());
        Set<Student> students = group.get().getStudents();
        students.remove(student.get());
        group.get().setStudents(students);
        adminService.saveStudent(student.get());
        adminService.saveGroup(group.get());
        return ResponseEntity.ok("\"Student has been assigned to another group\"");
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, ArrayList<String>> handle(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toCollection(ArrayList::new)
                        )
                ));
    }

}



