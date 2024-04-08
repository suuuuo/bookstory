package com.elice.bookstore.config.security.authentication.oauth2.dto;

import jakarta.validation.constraints.NotNull;

/**
 * store in a security context holder.
 *
 * @param userId general login: email, social login: provider + identity.
 * @param email .
 * @param name .
 * @param role .
 */
public record ResponseOauth2User(

    @NotNull(message = "userId is required.")
    String userId,

    @NotNull(message = "email is required.")
    String email,

    @NotNull(message = "name is required.")
    String name,

    @NotNull(message = "role is required.")
    String role) {
}
