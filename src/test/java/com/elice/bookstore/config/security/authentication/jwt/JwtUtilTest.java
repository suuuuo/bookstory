package com.elice.bookstore.config.security.authentication.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

  private JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    String secret = "BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234BookStory1234";
    jwtUtil = new JwtUtil(secret);
  }


  @DisplayName("토큰 타입, 이메일, 역할, 유효성을 검증한다.")
  @Test
  void createToken() {
    //given
    String jwt = jwtUtil.createJwt("access", "user1@gmail.com", "USER", 60 * 10 * 1000L);

    //when
    String type = jwtUtil.getType(jwt);
    String email = jwtUtil.getEmail(jwt);
    String role = jwtUtil.getRole(jwt);
    Boolean isValid = jwtUtil.isValid(jwt);

    //then
    assertThat(type).isEqualTo("access");
    assertThat(email).isEqualTo("user1@gmail.com");
    assertThat(role).isEqualTo("USER");
    assertThat(isValid).isEqualTo(true);
  }

  @DisplayName("토큰이 만료되었는지 검증한다.")
  @Test
  void checkTokenIsExpired() {
    //given
    String jwt = jwtUtil.createJwt("access", "user1@gmail.com", "USER", -10L);

    //when
    Boolean isValid = jwtUtil.isValid(jwt);

    //then
    assertThat(isValid).isEqualTo(false);
  }

}