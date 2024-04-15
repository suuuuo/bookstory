package com.elice.bookstore.config.security.filter;

import com.elice.bookstore.config.security.authentication.cookie.CookieUtil;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.jwt.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.jwt.refresh.dto.RequestLogin;
import com.elice.bookstore.config.security.authentication.jwt.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.jwt.refresh.repository.RefreshRepository;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * LoginFilter.
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  private final CookieUtil cookieUtil;

  /**
   * login filter.
   *
   * @param authenticationManager .
   * @param jwtUtil               .
   * @param refreshRepository     .
   * @param cookieUtil            .
   */
  public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                     RefreshRepository refreshRepository, CookieUtil cookieUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
    this.cookieUtil = cookieUtil;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {

    RequestLogin requestLogin = readByJson(request, response);

    if (requestLogin == null) {
      return null;
    }

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(requestLogin.email(), requestLogin.password());

    return authenticationManager.authenticate(authToken);
  }

  private RequestLogin readByJson(HttpServletRequest request, HttpServletResponse response) {

    RequestLogin requestLogin;
    PrintWriter writer;

    try {
      requestLogin = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

    if (Objects.isNull(requestLogin)
        || requestLogin.email().isBlank() || requestLogin.password().isBlank()) {
      try {
        writer = response.getWriter();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      writer.print("invalid input.");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      log.error("input data nothing.");
      return null;
    }

    return requestLogin;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult
  ) throws IOException, ServletException {

    CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

    String id = customUserDetails.getId();

    String role = customUserDetails.getAuthorities().iterator().next().getAuthority();

    log.info("id: {}, role: {}", id, role);

    ResponseCreateTokens tokens = createTokens(id, role);

    response.setHeader("access", tokens.accessToken());
    response.addCookie(
        cookieUtil.createCookie("refresh", tokens.refreshToken(),
            CookieUtil.REFRESH_TOKEN_EXPIRATION_TIME));
    response.setStatus(HttpStatus.OK.value());
  }

  private ResponseCreateTokens createTokens(String id, String role) {

    String accessToken = jwtUtil.createJwt("access", id, role, 60 * 10 * 1000L);

    String refreshToken = jwtUtil.createJwt("refresh", id, role, 60 * 60 * 24 * 1000L);

    Date date = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 1000L));
    Refresh refresh = new Refresh(id, refreshToken, date.toString());
    refreshRepository.save(refresh);

    return new ResponseCreateTokens(accessToken, refreshToken);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException failed
  ) throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(failed.getMessage());
  }
}