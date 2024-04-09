package com.elice.bookstore.config.security.authentication.oauth2.dto;

import java.util.Map;

/**
 * oauth2 user data from Google.
 */
public class ResponseGoogle implements ResponseOauth2 {

  private final Map<String, Object> attribute;

  public ResponseGoogle(Map<String, Object> attribute) {
    this.attribute = attribute;
  }

  @Override
  public String getProvider() {

    return "google";
  }

  @Override
  public String getProviderId() {

    return attribute.get("sub").toString();
  }

  @Override
  public String getEmail() {

    return attribute.get("email").toString();
  }

  @Override
  public String getName() {

    return attribute.get("name").toString();
  }
}
