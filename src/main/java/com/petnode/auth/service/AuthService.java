package com.petnode.auth.service;

import com.petnode.auth.dto.LoginRequest;
import com.petnode.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
