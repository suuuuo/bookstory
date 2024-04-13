package com.elice.bookstore.config.security.authentication.oauth2.dto;

import java.util.Map;

/**
 *  oauth2 user data from Naver.
 */
public class ResponseNaver implements ResponseOauth2 {

  private final Map<String, Object> attribute;

  public ResponseNaver(Map<String, Object> attribute) {

    this.attribute = attribute;
  }

  @Override
  public String getProvider() {

    return "naver";
  }

  @Override
  public String getProviderId() {

    return attribute.get("id").toString();
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
