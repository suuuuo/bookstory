package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.NotNull;

public record RequestDeleteUser(
    @NotNull(message = "password is required.")
    String password) {
}
