package com.demo.user.controller;

import com.demo.user.dto.request.CreateUserRequest;
import com.demo.user.dto.request.UpdateUserRequest;
import com.demo.user.dto.response.UserDetailsResponse;
import com.demo.user.generic.ApiResponse;
import com.demo.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("firstName");
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDetailsResponse response = userService.createUser(request);
        ApiResponse<UserDetailsResponse> apiResponse = ApiResponse.<UserDetailsResponse>builder().status(HttpStatus.CREATED.value()).success(true).message("User created successfully").data(response).timestamp(Instant.now()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {

        UserDetailsResponse response = userService.updateUser(id, request);
        ApiResponse<UserDetailsResponse> apiResponse = ApiResponse.<UserDetailsResponse>builder().status(HttpStatus.OK.value()).success(true).message("User data updated successfully").data(response).timestamp(Instant.now()).build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDetailsResponse>>> getUsers(

            @Min(value = 0, message = "Page number cannot be negative") @RequestParam(defaultValue = "0") int page,

            @Min(value = 1, message = "Page size must be at least 1") @Max(value = 100, message = "Page size cannot exceed 100") @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "firstName") String sortBy,

            @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Sort direction must be 'asc' or 'desc'") @RequestParam(defaultValue = "asc") String sortDir) {
        String normalizedSortBy = sortBy.trim();

        if (!ALLOWED_SORT_FIELDS.contains(normalizedSortBy)) {
            throw new IllegalArgumentException("Invalid sort field. Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), normalizedSortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserDetailsResponse> users = userService.getAllUsers(pageable);

        ApiResponse<Page<UserDetailsResponse>> response = ApiResponse.<Page<UserDetailsResponse>>builder().status(HttpStatus.OK.value()).success(true).message("Users retrieved successfully").data(users).failureMessage(null).timestamp(Instant.now()).build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailsResponse>> getUserById(@PathVariable UUID id) {

        UserDetailsResponse response = userService.getUserById(id);

        ApiResponse<UserDetailsResponse> apiResponse = ApiResponse.<UserDetailsResponse>builder().status(HttpStatus.OK.value()).success(true).message("User retrieved successfully").data(response).timestamp(Instant.now()).build();

        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable UUID id) {

        userService.deactivateUser(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder().status(HttpStatus.OK.value()).success(true).message("User deactivated successfully").timestamp(Instant.now()).build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreUser(@PathVariable UUID id) {

        userService.restoreUser(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder().status(HttpStatus.OK.value()).success(true).message("User restored successfully").timestamp(Instant.now()).build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder().status(HttpStatus.OK.value()).success(true).message("User permanently deleted successfully").timestamp(Instant.now()).build();

        return ResponseEntity.ok(response);
    }
}
