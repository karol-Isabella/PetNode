package com.petnode.service;

import com.petnode.dto.service.ServiceRequest;
import com.petnode.dto.service.ServiceResponse;

import java.util.List;

public interface ServiceManagementService {
    ServiceResponse create(ServiceRequest request);
    List<ServiceResponse> findAll();
    ServiceResponse update(String id, ServiceRequest request);
    void delete(String id);
}
