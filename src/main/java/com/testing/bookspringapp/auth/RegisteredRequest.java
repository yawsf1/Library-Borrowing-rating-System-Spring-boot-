package com.testing.bookspringapp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredRequest {
    @NotBlank(message = "Enter the username field")
    @Size(min = 3, max = 500, message = "Username length invalid")
    private String username;

    @NotBlank(message = "Enter the email field")
    @Email
    private String email;

    @NotBlank(message = "Enter the password field")
    @Size(min = 8, max = 500, message = "Password length invalid")
    private String password;
}
