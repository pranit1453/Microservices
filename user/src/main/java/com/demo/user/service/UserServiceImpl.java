package com.demo.user.service;

import com.demo.user.dto.request.CreateUserRequest;
import com.demo.user.dto.request.UpdateUserRequest;
import com.demo.user.dto.response.UserDetailsResponse;
import com.demo.user.exception.custom.*;
import com.demo.user.mapper.UserMapper;
import com.demo.user.model.User;
import com.demo.user.repository.UserJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    @Transactional
    @Override
    public UserDetailsResponse createUser(CreateUserRequest request) {

        if (request == null) {
            log.warn("Create user request cannot be null");
            throw new InvalidUserDataException("Create user request cannot be null");
        }

        log.info("Received request to create user with email: {}", request.email());

        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmail(email)) {
            log.warn("User already exists with email: {}", email);
            throw new UserAlreadyExistsException("User with email already exists: " + email);
        }

        try {
            User user = userMapper.toEntity(request);
            user.setEmail(email);
            User savedUser = userRepository.saveAndFlush(user);
            log.info("User created successfully with id: {}", savedUser.getId());
            return userMapper.toResponse(savedUser);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while creating user", ex);
            throw new DatabaseOperationException("Database constraint violation while creating user");
        }
    }

    @Transactional
    @Override
    public UserDetailsResponse updateUser(UUID id, UpdateUserRequest request) {

        if (id == null) {
            log.warn("User id for update cannot be null");
            throw new InvalidUserDataException("User id cannot be null");
        }

        if (request == null) {
            log.warn("User update request cannot be null");
            throw new InvalidUserDataException("User update request cannot be null");
        }

        log.info("Received request to update user with id: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found for update with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });

        try {
            userMapper.updateUserFromDto(request, user);
            if (request.email() != null && !request.email().isBlank()) {
                String email = normalizeEmail(request.email());
                if (!email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {
                    log.warn("Attempt to update with existing email: {}", email);
                    throw new UserAlreadyExistsException("User with email already exists: " + email);
                }
                user.setEmail(email);
            }
            User updated = userRepository.save(user);
            log.info("User updated successfully with id: {}", id);
            return userMapper.toResponse(updated);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while updating user", ex);
            throw new DatabaseOperationException("Database constraint violation while updating user");
        }
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        if (id == null) {
            log.warn("User id for permanent deletion cannot be null");
            throw new InvalidUserDataException("User id cannot be null");
        }
        log.info("Received request to permanently delete user with id: {}", id);
        User user = userRepository.findUserIncludingInactive(id).orElseThrow(() -> {
            log.warn("User not found for permanent deletion with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });
        try {
            userRepository.hardDeleteById(user.getId());
            log.info("User permanently deleted with id: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while deleting user permanently", ex);
            throw new DatabaseOperationException("Database constraint violation while deleting user permanently");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetailsResponse getUserById(UUID id) {
        if (id == null) {
            log.warn("User id cannot be null");
            throw new InvalidUserDataException("User id cannot be null");
        }
        log.info("Received request to get user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });
        log.info("Successfully retrieved data of user with id: {}", id);
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDetailsResponse> getAllUsers(Pageable pageable) {
        try {
            log.info("Fetching users - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
            Page<User> userPage = userRepository.findAll(pageable);
            return userPage.map(userMapper::toResponse);
        } catch (DataAccessException ex) {
            log.error("Database error while fetching users", ex);
            throw new DatabaseOperationException("Database error while fetching users");
        }
    }

    @Transactional
    @Override
    public void restoreUser(UUID id) {
        if (id == null) {
            log.warn("User id for activation cannot be null");
            throw new InvalidUserDataException("User id cannot be null");
        }
        log.info("Received request to activate user with id: {}", id);
        User user = userRepository.findUserIncludingInactive(id).orElseThrow(() -> {
            log.warn("User not found for activation with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });

        if (user.isActive()) {
            log.warn("User already active with id: {}", id);
            throw new UserAlreadyActiveException("User already active with id: " + id);
        }

        try {
            user.setActive(true);
            user.setDeletedAt(null);
            userRepository.save(user);
            log.info("User restored successfully with id: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while activating user", ex);
            throw new DatabaseOperationException("Database constraint violation while activating user");
        }
    }

    @Transactional
    @Override
    public void deactivateUser(UUID id) {
        if (id == null) {
            log.warn("User id for deactivation cannot be null");
            throw new InvalidUserDataException("User id cannot be null");
        }
        log.info("Received request to deactivate user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found for deactivation with id: {}", id);
            return new UserNotFoundException("User not found with id: " + id);
        });
        if (!user.isActive()) {
            log.warn("User already deactivated with id: {}", id);
            throw new UserAlreadyDeactivatedException("User already deactivated with id: " + id);
        }
        try {
            userRepository.deleteById(user.getId());
            log.info("User deactivated successfully with id: {}", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while deactivating user", ex);
            throw new DatabaseOperationException("Database constraint violation while deactivating user");
        }
    }
}
