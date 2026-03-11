package com.demo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "First name is required!")
        @Size(min = 2,max = 100,message = "First name must be between 2 and 100 characters")
        String firstname,

        @NotBlank(message = "Last name is required!")
        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        String lastname,

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
) {}
