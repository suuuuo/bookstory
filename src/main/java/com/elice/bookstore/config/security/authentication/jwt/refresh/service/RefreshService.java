package com.elice.bookstore.config.security.authentication.jwt.refresh.service;

import com.elice.bookstore.config.exception.domain.refresh.RefreshNotFoundCookieException;
import com.elice.bookstore.config.exception.domain.refresh.RefreshNotFoundException;
import com.elice.bookstore.config.exception.domain.refresh.RefreshTokenNotValidException;
import com.elice.bookstore.config.security.authentication.cookie.CookieUtil;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.jwt.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.jwt.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.jwt.refresh.repository.RefreshRepository;
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

  private final CookieUtil cookieUtil;

  /**
   * Refresh service dependency.

   * @param jwtUtil .
   * @param refreshRepository .
   * @param cookieUtil .
   */
  public RefreshService(JwtUtil jwtUtil, RefreshRepository refreshRepository,
                        CookieUtil cookieUtil) {
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
    this.cookieUtil = cookieUtil;
  }

  /**
   * reissue refresh token, access token.

   * @param cookies .
   * @return access token., refresh token.
   */
  @Transactional
  public ResponseCreateTokens reissue(Cookie[] cookies) {

    String refreshToken = cookieUtil.getValue(cookies, "refresh");

    checkTokenInCookie(refreshToken);

    validateRefreshToken(refreshToken);

    checkTokenInRepository(refreshToken);

    return issueNewTokens(jwtUtil, refreshToken);
  }

  /**
   * logout, server check & delete refresh token.

   * @param cookies .
   */
  @Transactional
  public void logout(Cookie[] cookies) {
    String refreshToken = cookieUtil.getValue(cookies, "refresh");

    checkTokenInCookie(refreshToken);

    checkTokenInRepository(refreshToken);

    refreshRepository.deleteByRefresh(refreshToken);
  }

  private void checkTokenInRepository(String refreshToken) {
    Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
    if (!isExist) {

      throw new RefreshNotFoundException();
    }
  }

  private void validateRefreshToken(String refreshToken) {
    if (!jwtUtil.isValid(refreshToken) || !"refresh".equals(jwtUtil.getType(refreshToken))) {

      throw new RefreshTokenNotValidException();
    }
  }

  private static void checkTokenInCookie(String refreshToken) {
    if (refreshToken == null) {

      throw new RefreshNotFoundCookieException();
    }
  }

  private ResponseCreateTokens issueNewTokens(JwtUtil jwtUtil, String refreshToken) {

    String userId = jwtUtil.getId(refreshToken);
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
