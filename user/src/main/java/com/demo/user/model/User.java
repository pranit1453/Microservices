package com.demo.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_user_phone", columnNames = "phone")
        },
        indexes = {
                @Index(name = "idx_user_active", columnList = "active")
        }
)
@SQLDelete(sql = "UPDATE users SET active = false, deleted_at = now() WHERE user_id = ?")
@SQLRestriction("active = true")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Pattern(
            regexp = "^[0-9]{10,13}$",
            message = "Phone number must contain only digits and be between 10 and 13 characters"
    )
    @Column(unique = true, length = 13)
    private String phone;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    protected void onCreate() {

        Instant now = Instant.now();

        if (this.createdAt == null)
            this.createdAt = now;

        this.updatedAt = now;

    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}