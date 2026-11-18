package com.petnode.owner.service.impl;

import com.petnode.owner.dto.OwnerRequest;
import com.petnode.owner.dto.OwnerResponse;
import com.petnode.owner.model.Owner;
import com.petnode.auth.model.User;
import com.petnode.auth.repository.UserRepository;
import com.petnode.owner.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OwnerResponse register(OwnerRequest request) {
        // Create user with encoded password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, "OWNER");
        User savedUser = userRepository.save(user);

        // Create owner
        Owner owner = new Owner(savedUser.getId(), request.getName(), request.getPhone(), request.getEmail());
        return convertToResponse(owner);
    }

    @Override
    public OwnerResponse findById(String id) {
        // Implementation: retrieve from database
        Owner owner = new Owner();
        owner.setId(id);
        return convertToResponse(owner);
    }

    @Override
    public List<OwnerResponse> findAll() {
        // Implementation: retrieve all owners from database
        return List.of();
    }

    @Override
    public OwnerResponse update(String id, OwnerRequest request) {
        // Implementation: update owner
        Owner owner = new Owner();
        owner.setId(id);
        owner.setName(request.getName());
        owner.setPhone(request.getPhone());
        owner.setEmail(request.getEmail());
        return convertToResponse(owner);
    }

    @Override
    public void delete(String id) {
        // Implementation: delete owner
    }

    private OwnerResponse convertToResponse(Owner owner) {
        return new OwnerResponse(owner.getId(), owner.getName(), owner.getEmail(), owner.getPhone());
    }
}
