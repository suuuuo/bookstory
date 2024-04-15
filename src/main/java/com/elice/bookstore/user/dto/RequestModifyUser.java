package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestModifyUser(
    @NotNull(message = "userName is required.")
    String userName,
    @NotNull(message = "dateOfBirth is required.")
    LocalDate dateOfBirth,
    @NotNull(message = "email is required.")
    String email,
    @NotNull(message = "poneNumber is required.")
    String phoneNumber,
    @NotNull(message = "address is required.")
    String address) {
}
