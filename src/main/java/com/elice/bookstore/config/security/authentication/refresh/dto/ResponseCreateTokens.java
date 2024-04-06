package com.elice.bookstore.config.security.authentication.refresh.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Refresh reissue must return tokens.
 *
 * @param accessToken .
 * @param refreshToken .
 */
public record ResponseCreateTokens(
    @NotNull(message = "accessToken is required.")
    String accessToken,
    @NotNull(message = "refreshToken is required.")
    String refreshToken) {
}
