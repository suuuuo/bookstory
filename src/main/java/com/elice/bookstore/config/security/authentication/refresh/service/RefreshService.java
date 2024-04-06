package com.elice.bookstore.config.security.authentication.refresh.service;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.refresh.repository.RefreshRepository;
import jakarta.servlet.http.Cookie;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * refresh token service.
 */
@Service
@Slf4j
public class RefreshService {

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  public RefreshService(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }

  /**
   * reissue refresh token, access token.

   * @param cookies .
   * @return access token., refresh token.
   */
  @Transactional
  public ResponseCreateTokens reissue(Cookie[] cookies) {

    String refreshToken = getRefreshToken(cookies);

    validateRefreshToken(refreshToken);

    return issueNewTokens(jwtUtil, refreshToken);
  }

  private String getRefreshToken(Cookie[] cookies) {

    String refreshToken = null;

    for (var e : cookies) {
      if (e.getName().equals("refresh")) {
        refreshToken = e.getValue();
      }
    }

    return refreshToken;
  }

  private void validateRefreshToken(String refreshToken) {
    if (refreshToken == null) {
      log.error("RefreshService, refreshToken null");

      throw new IllegalArgumentException("refresh token not found.");
    }

    if (!jwtUtil.isValid(refreshToken)) {
      log.error("RefreshService, refreshToken isExpired");

      throw new IllegalArgumentException("refresh token is expired.");
    }

    String type = jwtUtil.getType(refreshToken);
    if (!"refresh".equals(type)) {
      log.error("RefreshService, refreshToken type is not refresh: {}", type);

      throw new IllegalArgumentException("refresh token is not refresh.");
    }

    Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
    if (!isExist) {
      log.error("RefreshService, invalid refresh token");

      throw new IllegalArgumentException("invalid refresh token.");
    }
  }

  private ResponseCreateTokens issueNewTokens(JwtUtil jwtUtil, String refreshToken) {

    String email = jwtUtil.getEmail(refreshToken);
    String role = jwtUtil.getRole(refreshToken);

    String newAccessToken = jwtUtil.createJwt("access", email, role, 60 * 10 * 1000L);
    String newRefreshToken = jwtUtil.createJwt("refresh", email, role, 60 * 60 * 24 * 1000L);

    refreshRepository.deleteByRefresh(refreshToken);

    Date date = new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000L);

    Refresh newRefresh = new Refresh(email, newRefreshToken, date.toString());
    refreshRepository.save(newRefresh);
    return new ResponseCreateTokens(newAccessToken, newRefreshToken);
  }
}
