package com.petnode.service;

import com.petnode.dto.employee.EmployeeRequest;
import com.petnode.dto.employee.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse register(EmployeeRequest request);
    List<EmployeeResponse> findAll();
    EmployeeResponse update(String id, EmployeeRequest request);
    void toggleStatus(String id);
}
