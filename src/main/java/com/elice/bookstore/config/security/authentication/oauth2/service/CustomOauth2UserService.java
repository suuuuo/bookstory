package com.elice.bookstore.config.security.authentication.oauth2.service;

import com.elice.bookstore.config.security.authentication.oauth2.dto.ResponseGoogle;
import com.elice.bookstore.config.security.authentication.oauth2.dto.ResponseNaver;
import com.elice.bookstore.config.security.authentication.oauth2.dto.ResponseOauth2;
import com.elice.bookstore.config.security.authentication.oauth2.dto.ResponseOauth2User;
import com.elice.bookstore.config.security.authentication.user.CustomOauth2User;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * oauth2UserService.
 */
@Service
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  public CustomOauth2UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    ResponseOauth2 responseOauth = getResponseOauth(userRequest);

    if (responseOauth == null) {

      return null;
    }

    ResponseOauth2User responseOauth2User = storeUserFromOauth2User(responseOauth);

    return new CustomOauth2User(responseOauth2User);
  }

  private ResponseOauth2 getResponseOauth(OAuth2UserRequest userRequest) {

    OAuth2User oauth2User = super.loadUser(userRequest);

    log.info("oauth2User: {}", oauth2User);
    log.info("oAuthUser.getAttributes: {}", oauth2User.getAttributes());

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    if ("naver".equals(registrationId)) {

      Map<String, Object> attributes = oauth2User.getAttributes();

      return new ResponseNaver((Map<String, Object>) attributes.get("response"));
    }

    if ("google".equals(registrationId)) {

      return new ResponseGoogle(oauth2User.getAttributes());
    }

    log.error("registration id not enrolled.");
    return null;
  }

  private ResponseOauth2User storeUserFromOauth2User(ResponseOauth2 responseOauth) {

    String userId = responseOauth.getProvider() + " " + responseOauth.getProviderId();

    User user = userRepository.findByUserIdAndIsExist(userId, true).orElseGet(
        () -> {
          User newUser = new User(
              responseOauth.getName(), userId, null, null, responseOauth.getEmail(),
              null, null, 0L, Role.USER, true);

          return userRepository.save(newUser);
        }
    );

    return new ResponseOauth2User(
        String.valueOf(user.getId()),
        responseOauth.getEmail(),
        responseOauth.getName(),
        "ROLE_USER");
  }
}
