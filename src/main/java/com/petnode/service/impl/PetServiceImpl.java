package com.petnode.service.impl;

import com.petnode.dto.pet.PetRequest;
import com.petnode.dto.pet.PetResponse;
import com.petnode.model.Pet;
import com.petnode.repository.PetRepository;
import com.petnode.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public PetResponse register(PetRequest request) {
        Pet pet = new Pet(request.getOwnerId(), request.getName(), request.getBreed(),
                         request.getAge(), request.getGender());
        Pet saved = petRepository.save(pet);
        return convertToResponse(saved);
    }

    @Override
    public PetResponse findById(String id) {
        Pet pet = petRepository.findById(id).orElse(null);
        return pet != null ? convertToResponse(pet) : null;
    }

    @Override
    public List<PetResponse> findAll() {
        return petRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponse update(String id, PetRequest request) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            pet.setName(request.getName());
            pet.setBreed(request.getBreed());
            pet.setAge(request.getAge());
            pet.setGender(request.getGender());
            Pet updated = petRepository.save(pet);
            return convertToResponse(updated);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        petRepository.deleteById(id);
    }

    private PetResponse convertToResponse(Pet pet) {
        return new PetResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge(), pet.getGender(), pet.getOwnerId());
    }
}
