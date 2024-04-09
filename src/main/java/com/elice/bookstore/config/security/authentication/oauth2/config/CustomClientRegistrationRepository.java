package com.elice.bookstore.config.security.authentication.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * client registration repository.
 *
 */
@Configuration
public class CustomClientRegistrationRepository {

  private final SocialClientRegistration socialClientRegistration;

  public CustomClientRegistrationRepository(SocialClientRegistration socialClientRegistration) {
    this.socialClientRegistration = socialClientRegistration;
  }

  /**
   * OAuthLogin naver, google.
   *
   * @return .
   */
  public ClientRegistrationRepository clientRegistrationRepository() {

    return new InMemoryClientRegistrationRepository(
        socialClientRegistration.naverClientRegistration(),
        socialClientRegistration.googleClientRegistration());
  }
}
