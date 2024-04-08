package com.elice.bookstore.config.security.authentication.jwt.refresh.dto;

/**
 * when attempting authentication, read JSON data.
 */
public record RequestLogin(
    String userId,
    String password
) {
}
