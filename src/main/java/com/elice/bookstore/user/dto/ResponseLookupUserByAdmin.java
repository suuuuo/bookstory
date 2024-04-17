package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ResponseLookupUserByAdmin(
    @NotNull(message = "id is required.")
    Long id,
    @NotNull(message = "createdAt is required.")
    String createdAt,
    @NotNull(message = "email is required.")
    @Email(regexp = ".+@.+\\..+",
        message = "Email address is not formatted correctly. (ex: user@gmail.com)")
    String email,
    @NotNull(message = "userName is required.")
    String userName,
    @NotNull(message = "role is required.")
    String role,
    @NotNull(message = "loginType is required.")
    String loginType,
    @NotNull(message = "isExist is required.")
    boolean isExist) {
}