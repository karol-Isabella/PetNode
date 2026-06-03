package com.petnode.service;

import com.petnode.dto.auth.LoginRequest;
import com.petnode.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
