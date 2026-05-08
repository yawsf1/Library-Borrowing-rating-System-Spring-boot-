package com.testing.bookspringapp.user;

import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Enter the username field")
        @Size(min = 3, max = 500, message = "Username length invalid")
        String username,

        @NotBlank(message = "Enter the email field")
        @Email
        String email,

        @NotBlank(message = "Enter the password field")
        @Size(min = 8, max = 500, message = "Password length invalid")
        String password

) {
}
