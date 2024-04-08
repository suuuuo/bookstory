package com.elice.bookstore.config.security.authentication.oauth2.dto;

import jakarta.validation.constraints.NotNull;

/**
 * store in a security context holder.
 *
 * @param id .
 * @param email .
 * @param name .
 * @param role .
 */
public record ResponseOauth2User(

    @NotNull(message = "id is required.")
    String id,

    @NotNull(message = "email is required.")
    String email,

    @NotNull(message = "name is required.")
    String name,

    @NotNull(message = "role is required.")
    String role) {
}
