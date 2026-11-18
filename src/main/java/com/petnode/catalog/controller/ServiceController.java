package com.petnode.catalog.controller;

import com.petnode.catalog.dto.ServiceRequest;
import com.petnode.catalog.dto.ServiceResponse;
import com.petnode.catalog.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @PostMapping
    public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest request) {
        ServiceResponse response = serviceManagementService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll() {
        List<ServiceResponse> response = serviceManagementService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(@PathVariable String id, @RequestBody ServiceRequest request) {
        ServiceResponse response = serviceManagementService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        serviceManagementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
