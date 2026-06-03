package com.petnode.service.impl;

import com.petnode.dto.service.ServiceRequest;
import com.petnode.dto.service.ServiceResponse;
import com.petnode.model.Service;
import com.petnode.model.ServiceType;
import com.petnode.repository.ServiceRepository;
import com.petnode.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceManagementServiceImpl implements ServiceManagementService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public ServiceResponse create(ServiceRequest request) {
        Service service = new Service(
                request.getName(), request.getDescription(), request.getPrice(), ServiceType.GROOMING);
        Service saved = serviceRepository.save(service);
        return convertToResponse(saved);
    }

    @Override
    public List<ServiceResponse> findAll() {
        return serviceRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponse update(String id, ServiceRequest request) {
        Service service = serviceRepository.findById(id).orElse(null);
        if (service != null) {
            service.setName(request.getName());
            service.setDescription(request.getDescription());
            service.setPrice(request.getPrice());
            Service updated = serviceRepository.save(service);
            return convertToResponse(updated);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        serviceRepository.deleteById(id);
    }

    private ServiceResponse convertToResponse(Service service) {
        return new ServiceResponse(service.getId(), service.getName(), service.getPrice());
    }
}
