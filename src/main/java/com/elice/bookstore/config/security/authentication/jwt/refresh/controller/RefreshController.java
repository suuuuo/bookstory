package com.elice.bookstore.config.security.authentication.jwt.refresh.controller;

import com.elice.bookstore.config.security.authentication.jwt.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.jwt.refresh.service.RefreshService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * reissue utils by refresh token.
 */
@RestController
public class RefreshController {

  private final RefreshService refreshService;

  public RefreshController(RefreshService refreshService) {
    this.refreshService = refreshService;
  }

  /**
   * reissue access token by refresh token.

   * @param request .
   * @param response .
   * @return reissue new access token.
   */
  @PostMapping("/api/v1/tokens/reissue")
  public ResponseEntity<Void> reissue(HttpServletRequest request, HttpServletResponse response) {

    ResponseCreateTokens reissuedTokens = refreshService.reissue(request.getCookies());
    response.setHeader("access", reissuedTokens.accessToken());
    response.addCookie(createCookie(reissuedTokens.refreshToken()));

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * client requests logout because server manages refresh token.

   * @param request for cookies.
   * @param response .
   *
   * @return .
   */
  @PostMapping("/api/v1/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

    refreshService.logout(request.getCookies());
    response.addCookie(deleteCookie());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  private Cookie createCookie(String value) {
    Cookie cookie = new Cookie("refresh", value);
    cookie.setMaxAge(60 * 60 * 24);
    cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }

  private static Cookie deleteCookie() {
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    return cookie;
  }
}