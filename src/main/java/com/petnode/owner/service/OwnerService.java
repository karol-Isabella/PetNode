package com.petnode.owner.service;

import com.petnode.owner.dto.OwnerRequest;
import com.petnode.owner.dto.OwnerResponse;

import java.util.List;

public interface OwnerService {
    OwnerResponse register(OwnerRequest request);
    OwnerResponse findById(String id);
    List<OwnerResponse> findAll();
    OwnerResponse update(String id, OwnerRequest request);
    void delete(String id);
}
