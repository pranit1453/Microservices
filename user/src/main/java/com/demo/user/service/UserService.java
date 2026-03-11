package com.demo.user.service;

import com.demo.user.dto.request.CreateUserRequest;
import com.demo.user.dto.request.UpdateUserRequest;
import com.demo.user.dto.response.UserDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserDetailsResponse createUser(CreateUserRequest request);

    UserDetailsResponse updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);

    UserDetailsResponse getUserById(UUID id);

    Page<UserDetailsResponse> getAllUsers(Pageable pageable);

    void restoreUser(UUID id);

    void deactivateUser(UUID id);
}
