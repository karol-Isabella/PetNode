package com.petnode.pet.controller;

import com.petnode.pet.dto.PetRequest;
import com.petnode.pet.dto.PetResponse;
import com.petnode.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetResponse> register(@RequestBody PetRequest request) {
        PetResponse response = petService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findById(@PathVariable String id) {
        PetResponse response = petService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> findAll() {
        List<PetResponse> response = petService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable String id, @RequestBody PetRequest request) {
        PetResponse response = petService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
