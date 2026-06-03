package com.petnode.dto.employee;

public class EmployeeRequest {
    private String name;
    private String specialty;
    private String phone;

    public EmployeeRequest() {}

    public EmployeeRequest(String name, String specialty, String phone) {
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
