package com.petnode.dto.employee;

public class EmployeeResponse {
    private String id;
    private String name;
    private String specialty;
    private Boolean active;

    public EmployeeResponse() {}

    public EmployeeResponse(String id, String name, String specialty, Boolean active) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
