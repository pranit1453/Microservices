package com.demo.auth.dto.response;

import com.demo.auth.model.enums.Provider;
import com.demo.auth.model.enums.Role;

import java.time.Instant;
import java.util.UUID;

public record AuthResponse(
        UUID id,
        UUID userId,
        String username,
        String email,
        Role role,
        Provider provider,
        boolean enabled,
        boolean accountNonLocked,
        Instant lastLoginAt,
        Instant createdAt

) {
}
