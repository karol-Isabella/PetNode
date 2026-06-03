package com.petnode.service;

import com.petnode.dto.owner.OwnerRequest;
import com.petnode.dto.owner.OwnerResponse;

import java.util.List;

public interface OwnerService {
    OwnerResponse register(OwnerRequest request);
    OwnerResponse findById(String id);
    List<OwnerResponse> findAll();
    OwnerResponse update(String id, OwnerRequest request);
    void delete(String id);
}
