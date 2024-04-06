package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.NotNull;

public record ResponseRegisterUser(
    @NotNull(message = "UserName is required.")
    String userName
    ) {
}
