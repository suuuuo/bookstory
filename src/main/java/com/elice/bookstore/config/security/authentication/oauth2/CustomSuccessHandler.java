package com.elice.bookstore.config.security.authentication.oauth2;

import com.elice.bookstore.config.security.authentication.cookie.CookieUtil;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.jwt.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.jwt.refresh.repository.RefreshRepository;
import com.elice.bookstore.config.security.authentication.user.CustomOauth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * success handler.
 */
@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  private final CookieUtil cookieUtil;

  /**
   * dependency.
   *
   * @param jwtUtil .
   * @param refreshRepository .
   * @param cookieUtil .
   */
  public CustomSuccessHandler(
      JwtUtil jwtUtil, RefreshRepository refreshRepository, CookieUtil cookieUtil) {
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
    this.cookieUtil = cookieUtil;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    CustomOauth2User principal = (CustomOauth2User) authentication.getPrincipal();
    String id = principal.getId();
    String role = principal.getAuthorities().iterator().next().getAuthority();

    log.info("id:{}", id);
    log.info("role:{}", role);

    String refreshToken = createRefreshToken(id, role);

    response.addCookie(
        cookieUtil.createCookie("refresh", refreshToken, CookieUtil.REFRESH_TOKEN_EXPIRATION_TIME));
    response.sendRedirect("http://localhost:5173/social_login_handler?social_login=success");
  }

  private String createRefreshToken(String id, String role) {
    String refreshToken = jwtUtil.createJwt("refresh", id, role, 60 * 60 * 24 * 1000L);

    Date date = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 1000L));
    Refresh refresh = new Refresh(id, refreshToken, date.toString());
    refreshRepository.save(refresh);

    return refreshToken;
  }
}
