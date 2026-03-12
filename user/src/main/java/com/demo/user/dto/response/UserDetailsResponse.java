package com.demo.user.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UserDetailsResponse(
        UUID id,
        String username,
        String firstname,
        String lastname,
        String email,
        String phone,
        String address,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
