package com.petnode.controller;

import com.petnode.dto.owner.OwnerRequest;
import com.petnode.dto.owner.OwnerResponse;
import com.petnode.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerResponse> register(@RequestBody OwnerRequest request) {
        OwnerResponse response = ownerService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> findById(@PathVariable String id) {
        OwnerResponse response = ownerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> findAll() {
        List<OwnerResponse> response = ownerService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> update(@PathVariable String id, @RequestBody OwnerRequest request) {
        OwnerResponse response = ownerService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
