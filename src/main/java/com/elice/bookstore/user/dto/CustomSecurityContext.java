package com.elice.bookstore.user.dto;

import jakarta.validation.constraints.NotNull;

/**
 * loginfor user controller.
 *
 * @param id .
 * @param auth .
 */
public record CustomSecurityContext(
    @NotNull(message = "id is required.")
    Long id,
    @NotNull(message = "auth is required.")
    String auth
) {
}
