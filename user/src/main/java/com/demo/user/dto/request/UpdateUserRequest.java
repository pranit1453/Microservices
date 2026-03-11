package com.demo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(min = 2,max = 100,message = "First name must be between 2 and 100 characters")
        String firstname,

        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        String lastname,

        @Email(message = "Invalid email format")
        @Size(max = 150)
        String email,

        @Pattern(
                regexp = "^[0-9]{10,13}$",
                message = "Phone number must contain only digits and must be between 10 and 13 characters"
        )
        String phone,

        @Size(max = 255, message = "Address cannot exceed 255 characters")
        String address,

        Boolean active
) {
}
