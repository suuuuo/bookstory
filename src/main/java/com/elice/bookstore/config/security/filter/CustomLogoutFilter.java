package com.elice.bookstore.config.security.filter;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.refresh.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

/**
 * custom logout filter.
 */

@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  public CustomLogoutFilter(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }

  private void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String requestUri = request.getRequestURI();
    String requestMethod = request.getMethod();

    if (!requestUri.matches("^/logout$") && requestMethod.equals("POST")) {

      log.info("CustomLogoutFilter, /logout [POST] not match.");
      chain.doFilter(request, response);
      return;
    }

    String refreshToken = getRefreshToken(request.getCookies());

    if (isInvalidRefreshToken(refreshToken, response)) {
      return;
    }

    refreshRepository.deleteByRefresh(refreshToken);

    response.addCookie(deleteCookie());
    response.setStatus(HttpServletResponse.SC_OK);
  }

  private boolean isInvalidRefreshToken(String refreshToken, HttpServletResponse response)
      throws IOException {

    PrintWriter writer = response.getWriter();

    if (refreshToken == null) {
      log.error("logout filter, refreshToken null");

      writer.print("refresh token is null.");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return true;
    }

    if (!jwtUtil.isValid(refreshToken)) {
      log.error("logout filter, refreshToken is invalid");

      writer.print("refresh token is invalid.");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return true;
    }

    String type = jwtUtil.getType(refreshToken);
    if (!"refresh".equals(type)) {
      log.error("logout filter, refreshToken type is not refresh: {}", type);

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      writer.print("refresh token is invalid.");
      return true;
    }

    Boolean isExist = refreshRepository.existsByRefresh(refreshToken);
    if (!isExist) {
      log.error("logout filter, invalid refresh token");

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      writer.print("refresh token is invalid.");
      return true;
    }

    return false;
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

  private static Cookie deleteCookie() {
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    return cookie;
  }
}
