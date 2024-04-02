package com.elice.bookstore.config.security;

import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.refresh.repository.RefreshRepository;
import com.elice.bookstore.config.security.filter.CustomLogoutFilter;
import com.elice.bookstore.config.security.filter.JwtFilter;
import com.elice.bookstore.config.security.filter.LoginFilter;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Global Security Config.
 */
@Configuration
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  /**
   * Security Config Dependency.
   *
   * @param authenticationConfiguration .
   * @param jwtUtil                     .
   * @param refreshRepository           .
   */
  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                        JwtUtil jwtUtil, RefreshRepository refreshRepository) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
  }

  /**
   * security filter chain.

   * @param http .
   * @return .
   * @throws Exception .
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .cors((cors) -> cors
            .configurationSource(request -> {

              CorsConfiguration config = new CorsConfiguration();

              config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
              config.setAllowedMethods(Collections.singletonList("*"));
              config.setAllowCredentials(true);
              config.setAllowedHeaders(Collections.singletonList("*"));
              config.setMaxAge(60 * 60L);
              config.setExposedHeaders(Collections.singletonList("Authorization"));

              return config;
            }));

    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);

    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/v1/login", "/", "/v1/signup", "/v1/reissue").permitAll()
            .requestMatchers("/v1/admin").hasRole("ADMIN")
            .anyRequest().authenticated());

    http
        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

    http
        .addFilterAt(
            new LoginFilter(authenticationManager(authenticationConfiguration),
                jwtUtil, refreshRepository),
            UsernamePasswordAuthenticationFilter.class);

    http
        .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

    http
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bcryptPasswordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {

    return configuration.getAuthenticationManager();
  }
}