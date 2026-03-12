package com.demo.auth.model.entities;

import com.demo.auth.model.enums.Provider;
import com.demo.auth.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "auth_users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_auth_user_id", columnNames = "user_id"),
                @UniqueConstraint(name = "uk_auth_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_auth_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_auth_username", columnList = "username"),
                @Index(name = "idx_auth_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "auth_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    @Pattern(
            regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can contain only letters, numbers, dots and underscores"
    )
    @Column(nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Provider provider = Provider.SELF;

    @Column(name = "provider_id")
    private String providerId;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
