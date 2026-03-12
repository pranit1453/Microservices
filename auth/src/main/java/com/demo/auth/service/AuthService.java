package com.demo.auth.service;

import com.demo.auth.dto.request.SignupRequest;
import com.demo.auth.dto.response.AuthResponse;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse createNewUser(@Valid SignupRequest request);
}
