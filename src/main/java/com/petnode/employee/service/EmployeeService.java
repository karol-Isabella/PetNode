package com.petnode.employee.service;

import com.petnode.employee.dto.EmployeeRequest;
import com.petnode.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse register(EmployeeRequest request);
    List<EmployeeResponse> findAll();
    EmployeeResponse update(String id, EmployeeRequest request);
    void toggleStatus(String id);
}
