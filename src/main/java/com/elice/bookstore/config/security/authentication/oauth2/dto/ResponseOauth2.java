package com.elice.bookstore.config.security.authentication.oauth2.dto;

/**
 * required by each provider to obtain user information.
 */
public interface ResponseOauth2 {

  String getProvider();

  String getProviderId();

  String getEmail();

  String getName();
}
