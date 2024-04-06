package com.elice.bookstore.config.security.filter;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.refresh.domain.Refresh;
import com.elice.bookstore.config.security.authentication.refresh.dto.ResponseCreateTokens;
import com.elice.bookstore.config.security.authentication.refresh.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * LoginFilter.
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  /**
   * login filter.

   * @param authenticationManager .
   * @param jwtUtil .
   * @param refreshRepository .
   */
  public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                     RefreshRepository refreshRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {

    System.out.println("LoginFilter.attemptAuthentication");
    String email = request.getParameter("email");
    String password = obtainPassword(request);

    log.info("attemptAuthentication {}", email);
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(email, password);

    return authenticationManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult
  ) throws IOException, ServletException {

    String email = authResult.getName();

    Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    ResponseCreateTokens tokens = createTokens(email, role);

    response.setHeader("access", tokens.accessToken());
    response.addCookie(createCookie(tokens.refreshToken()));
    response.setStatus(HttpStatus.OK.value());
  }

  private ResponseCreateTokens createTokens(String email, String role) {
    String accessToken = jwtUtil.createJwt("access", email, role, 60 * 10 * 1000L);

    String refreshToken = jwtUtil.createJwt("refresh", email, role, 60 * 60 * 24 * 1000L);

    Date date = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 1000L));
    Refresh refresh = new Refresh(email, refreshToken, date.toString());
    refreshRepository.save(refresh);

    return new ResponseCreateTokens(accessToken, refreshToken);
  }

  private Cookie createCookie(String value) {
    Cookie cookie = new Cookie("refresh", value);
    cookie.setMaxAge(60 * 60 * 24);
    cookie.setHttpOnly(true);

    return cookie;
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException failed
  ) throws IOException, ServletException {

    response.setStatus(401);
  }
}