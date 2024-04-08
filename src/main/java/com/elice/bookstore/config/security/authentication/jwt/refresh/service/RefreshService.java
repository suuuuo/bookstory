package com.elice.bookstore.config.security.authentication.jwt.refresh.service;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.jwt.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.jwt.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.jwt.refresh.repository.RefreshRepository;
import jakarta.servlet.http.Cookie;
import java.util.Date;
import java.util.Objects;
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

  /**
   * logout, server check & delete refresh token.

   * @param cookies .
   */
  @Transactional
  public void logout(Cookie[] cookies) {
    String refreshToken = getRefreshToken(cookies);

    validateRefreshToken(refreshToken); // 유효기간은 검증할 필요는 없음. 재사용 할 수 있게 분리해야함.

    refreshRepository.deleteByRefresh(refreshToken);
  }

  private String getRefreshToken(Cookie[] cookies) {

    String refreshToken = null;

    if (Objects.isNull(cookies)) {

      return null;
    }

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

    String userId = jwtUtil.getUserId(refreshToken);
    String role = jwtUtil.getRole(refreshToken);

    String newAccessToken = jwtUtil.createJwt("access", userId, role, 60 * 10 * 1000L);
    String newRefreshToken = jwtUtil.createJwt("refresh", userId, role, 60 * 60 * 24 * 1000L);

    refreshRepository.deleteByRefresh(refreshToken);

    Date date = new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000L);

    Refresh newRefresh = new Refresh(userId, newRefreshToken, date.toString());
    refreshRepository.save(newRefresh);
    return new ResponseCreateTokens(newAccessToken, newRefreshToken);
  }
}
