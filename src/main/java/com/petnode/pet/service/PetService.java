package com.petnode.pet.service;

import com.petnode.pet.dto.PetRequest;
import com.petnode.pet.dto.PetResponse;

import java.util.List;

public interface PetService {
    PetResponse register(PetRequest request);
    PetResponse findById(String id);
    List<PetResponse> findAll();
    PetResponse update(String id, PetRequest request);
    void delete(String id);
}
