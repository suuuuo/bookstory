package com.elice.bookstore.config.security.filter;

import com.elice.bookstore.config.security.authentication.CustomUserDetails;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * JwtFilter.
 */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken = request.getHeader("access");

    if (accessToken == null) {

      log.info("access token null");
      filterChain.doFilter(request, response);
      return;
    }

    if (isInvalidAccessToken(accessToken, response)) {
      return;
    }

    setAuthenticationFromToken(jwtUtil, accessToken);

    filterChain.doFilter(request, response);
  }

  private void setAuthenticationFromToken(JwtUtil jwtUtil, String accessToken) {

    String email = jwtUtil.getEmail(accessToken);
    String role = jwtUtil.getRole(accessToken);

    User user = new User(email, Role.valueOf(role));
    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        customUserDetails, null, customUserDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private boolean isInvalidAccessToken(String accessToken, HttpServletResponse response)
      throws IOException {

    if (!jwtUtil.isValid(accessToken)) {
      log.error("JwtFilter, refreshToken is invalid");

      PrintWriter writer = response.getWriter();
      writer.print("access token is invalid.");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return true;
    }

    String type = jwtUtil.getType(accessToken);
    if (!"access".equals(type)) {
      log.error("RefreshService, refreshToken type is not refresh: {}", type);

      PrintWriter writer = response.getWriter();
      writer.print("access token is invalid.");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return true;
    }
    return false;
  }
}
