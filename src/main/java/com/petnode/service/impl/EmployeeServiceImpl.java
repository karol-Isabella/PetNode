package com.petnode.service.impl;

import com.petnode.dto.employee.EmployeeRequest;
import com.petnode.dto.employee.EmployeeResponse;
import com.petnode.model.Employee;
import com.petnode.repository.EmployeeRepository;
import com.petnode.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse register(EmployeeRequest request) {
        Employee employee = new Employee(request.getName(), request.getPhone(), request.getSpecialty(), request.getSpecialty());
        Employee saved = employeeRepository.save(employee);
        return convertToResponse(saved);
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse update(String id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setName(request.getName());
            employee.setPhone(request.getPhone());
            employee.setSpecialty(request.getSpecialty());
            Employee updated = employeeRepository.save(employee);
            return convertToResponse(updated);
        }
        return null;
    }

    @Override
    public void toggleStatus(String id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setActive(!employee.getActive());
            employeeRepository.save(employee);
        }
    }

    private EmployeeResponse convertToResponse(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getSpecialty(), employee.getActive());
    }
}
