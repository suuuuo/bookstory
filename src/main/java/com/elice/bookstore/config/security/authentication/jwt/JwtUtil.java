package com.elice.bookstore.config.security.authentication.jwt;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT Utility.
 */
@Component
@Slf4j
public class JwtUtil {

  private final SecretKey secretKey;

  /**
   * JwtUtil constructor.

   * @param secret private_key.
   */
  public JwtUtil(@Value("${spring.jwt.secret}") String secret) {

    secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  /**
   * get userId from token.
   *
   * @param token access, refresh token.
   * @return userId.
   */
  public String getUserId(String token) {

    return Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(token).getPayload().get("userId", String.class);
  }

  /**
   * get role from token.

   * @param token access, refresh token.
   * @return role.
   */
  public String getRole(String token) {

    return Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(token).getPayload().get("role", String.class);
  }

  /**
   * token validation.

   * @param token access, refresh token.
   * @return if valid return true;
   */
  public Boolean isValid(String token) {
    try {
      Jwts.parser().verifyWith(secretKey).build()
          .parseSignedClaims(token);
    } catch (Exception e) {

      log.error("token is invalid: {}", e.getMessage());
      return false;
    }

    return true;
  }

  /**
   * get type from token.

   * @param token access, refresh token.
   * @return type.
   */
  public String getType(String token) {

    return Jwts.parser().verifyWith(secretKey).build()
        .parseSignedClaims(token).getPayload().get("type", String.class);
  }

  /**
   * create jwt token.

   * @param userId .
   * @param role .
   * @param expiredMs .
   * @return jwt access, refresh token.
   */
  public String createJwt(String type, String userId, String role, Long expiredMs) {

    return Jwts.builder()
        .claim("type", type)
        .claim("userId", userId)
        .claim("role", role)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }
}
