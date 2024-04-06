package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

/**
 * RegisterForm
 * default
 *   - point(0), role(USER), isExist(true),
 *   - userId = email
 *   - social login userId = provider + providerId.
 */
public record RequestRegisterUser(
    @NotNull(message = "UserName is required.")
    String userName,

    @NotNull(message = "Password is required.")
    String password,

    @NotNull(message = "Password is required.")
    String passwordCheck,

    @Past(message = "The date of birth must be earlier than the current time.")
    LocalDate dateOfBirth,
    @NotNull(message = "Email is required.")
    @Email(regexp = ".+@.+\\..+",
        message = "Email address is not formatted correctly. (ex: user@gmail.com)")
    String email,
    @NotNull(message = "PhoneNumber is required.")
    String phoneNumber,
    String address) {
}
