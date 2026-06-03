package com.petnode.service;

import com.petnode.dto.pet.PetRequest;
import com.petnode.dto.pet.PetResponse;

import java.util.List;

public interface PetService {
    PetResponse register(PetRequest request);
    PetResponse findById(String id);
    List<PetResponse> findAll();
    PetResponse update(String id, PetRequest request);
    void delete(String id);
}
