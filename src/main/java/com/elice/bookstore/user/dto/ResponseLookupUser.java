package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ResponseLookupUser(
    @NotNull(message = "userName is required.")
    String userName,
    @NotNull(message = "dateOfBirth is required.")
    LocalDate dateOfBirth,
    @NotNull(message = "email is required.")
    @Email(regexp = ".+@.+\\..+",
        message = "Email address is not formatted correctly. (ex: user@gmail.com)")
    String email,
    @NotNull(message = "poneNumber is required.")
    String phoneNumber,
    @NotNull(message = "address is required.")
    String address,
    @NotNull(message = "point is required.")
    Long point,
    String role) {
}