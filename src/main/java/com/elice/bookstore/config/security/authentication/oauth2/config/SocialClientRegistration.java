package com.elice.bookstore.config.security.authentication.oauth2.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Component;

/**
 * oauth2 registration, provider config.
 */
@Component
public class SocialClientRegistration {

  /**
   * oauth2 login naver.
   *
   */
  public ClientRegistration naverClientRegistration() {

    return ClientRegistration.withRegistrationId("naver")
        .clientId("lInvrg1suGNqFwAbBMBR")
        .clientSecret("vKBY3GO7Yt")
        .redirectUri("http://localhost:8080/login/oauth2/code/naver")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("name", "email")
        .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
        .tokenUri("https://nid.naver.com/oauth2.0/token")
        .userInfoUri("https://openapi.naver.com/v1/nid/me")
        .userNameAttributeName("response")
        .build();
  }

  /**
   * oauth2 login google.
   *
   */
  public ClientRegistration googleClientRegistration() {

    return ClientRegistration.withRegistrationId("google")
        .clientId("15683748038-co2cuhlfdt91mis4na6pvmlfi6gc9fbq.apps.googleusercontent.com")
        .clientSecret("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")
        .redirectUri("http://localhost:8080/login/oauth2/code/google")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("profile", "email")
        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
        .tokenUri("https://www.googleapis.com/oauth2/v4/token")
        .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
        .issuerUri("https://accounts.google.com")
        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
        .userNameAttributeName(IdTokenClaimNames.SUB)
        .build();
  }
}
