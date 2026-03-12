package com.demo.auth.service;

import com.demo.auth.client.UserServiceClient;
import com.demo.auth.dto.request.CreateUserProfileRequest;
import com.demo.auth.dto.request.SignupRequest;
import com.demo.auth.dto.response.AuthResponse;
import com.demo.auth.dto.response.UserDetailsResponse;
import com.demo.auth.mapper.AuthMapper;
import com.demo.auth.model.entities.AuthUser;
import com.demo.auth.repository.AuthJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthJpaRepository authRepository;
    private final AuthMapper authMapper;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse createNewUser(SignupRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create account request cannot be null");
        }

        if (request.email() == null || request.username() == null || request.password() == null || request.phone() == null) {
            throw new IllegalArgumentException("Username,Email,Phone,Password fields cannot be null");
        }

        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();
        if (authRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this email is already exists");
        }

        if (authRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User with this username already exists");
        }

        try {
            // map to user service
            CreateUserProfileRequest userProfileRequest = authMapper.toCreateUserProfileRequest(request);

            // Call user service
            UserDetailsResponse userResponse = userServiceClient.createUser(userProfileRequest);

            UUID userId = userResponse.id();

            AuthUser authUser = authMapper.toAuthUser(request);
            authUser.setUserId(userId);
            authUser.setPassword(passwordEncoder.encode(request.password()));

            AuthUser savedUser = authRepository.save(authUser);

            // Generate token

            // Build Response
        } catch (DataIntegrityViolationException ex) {

        }

    }
}
