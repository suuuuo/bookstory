package com.elice.bookstore.config.security.authentication.user;

import com.elice.bookstore.config.security.authentication.oauth2.dto.ResponseOauth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * CustomOauth2User.
 */
public class CustomOauth2User implements OAuth2User {

  private final ResponseOauth2User responseOauth2User;

  public CustomOauth2User(ResponseOauth2User responseOauth2User) {
    this.responseOauth2User = responseOauth2User;
  }

  @Override
  public <A> A getAttribute(String name) {
    return OAuth2User.super.getAttribute(name);
  }

  @Override
  public Map<String, Object> getAttributes() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add((GrantedAuthority) responseOauth2User::role);

    return collection;
  }

  @Override
  public String getName() {

    return responseOauth2User.name();
  }

  public String getUserId() {

    return responseOauth2User.userId();
  }
}
