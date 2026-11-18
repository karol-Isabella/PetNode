package com.petnode.pet.repository;

import com.petnode.pet.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByName(String name);
    List<Pet> findByOwnerId(String ownerId);
}
