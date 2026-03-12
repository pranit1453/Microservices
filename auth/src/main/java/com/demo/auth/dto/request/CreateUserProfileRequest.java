package com.demo.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserProfileRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(
                regexp = "^[a-zA-Z0-9._]+$",
                message = "Username can contain only letters, numbers, dots and underscores"
        )
        String username,
        @NotBlank(message = "First name is required!")
        @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required!")
        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        String lastName,
        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email format")
        @Size(max = 150)
        String email,

        @NotBlank(message = "Phone number is required!")
        @Pattern(
                regexp = "^[0-9]{10,13}$",
                message = "Phone number must contain only digits and must be between 10 and 13 characters"
        )
        String phone,

        @NotBlank(message = "Address is required!")
        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address
) {
}
