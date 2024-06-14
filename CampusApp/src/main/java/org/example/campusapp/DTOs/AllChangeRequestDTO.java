package org.example.campusapp.DTOs;

import java.util.List;

public class AllChangeRequestDTO {
    private List<String> allRequests;

    public AllChangeRequestDTO() {

    }

    public List<String> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<String> allRequests) {
        this.allRequests = allRequests;
    }
}
