package com.petnode.catalog.service;

import com.petnode.catalog.dto.ServiceRequest;
import com.petnode.catalog.dto.ServiceResponse;

import java.util.List;

public interface ServiceManagementService {
    ServiceResponse create(ServiceRequest request);
    List<ServiceResponse> findAll();
    ServiceResponse update(String id, ServiceRequest request);
    void delete(String id);
}
