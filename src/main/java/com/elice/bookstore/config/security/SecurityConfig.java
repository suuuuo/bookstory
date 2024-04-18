package com.elice.bookstore.config.security;

import com.elice.bookstore.config.security.authentication.cookie.CookieUtil;
import com.elice.bookstore.config.security.authentication.jwt.JwtUtil;
import com.elice.bookstore.config.security.authentication.jwt.refresh.repository.RefreshRepository;
import com.elice.bookstore.config.security.authentication.oauth2.CustomSuccessHandler;
import com.elice.bookstore.config.security.authentication.oauth2.config.CustomClientRegistrationRepository;
import com.elice.bookstore.config.security.authentication.oauth2.service.CustomOauth2UserService;
import com.elice.bookstore.config.security.filter.CustomLogoutFilter;
import com.elice.bookstore.config.security.filter.JwtFilter;
import com.elice.bookstore.config.security.filter.LoginFilter;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity(debug = false)
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;

  private final JwtUtil jwtUtil;

  private final RefreshRepository refreshRepository;

  private final CustomOauth2UserService customOauth2UserService;

  private final CustomClientRegistrationRepository clientRegistrationRepository;

  private final CustomSuccessHandler customSuccessHandler;

  private final CookieUtil cookieUtil;

  /**
   * Security Config Dependency.
   *
   * @param authenticationConfiguration  .
   * @param jwtUtil                      .
   * @param refreshRepository            .
   * @param customOauth2UserService      .
   * @param clientRegistrationRepository .
   * @param customSuccessHandler         .
   * @param cookieUtil                   .
   */
  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
      JwtUtil jwtUtil, RefreshRepository refreshRepository,
      CustomOauth2UserService customOauth2UserService,
      CustomClientRegistrationRepository clientRegistrationRepository,
      CustomSuccessHandler customSuccessHandler, CookieUtil cookieUtil) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
    this.customOauth2UserService = customOauth2UserService;
    this.clientRegistrationRepository = clientRegistrationRepository;
    this.customSuccessHandler = customSuccessHandler;
    this.cookieUtil = cookieUtil;
  }

  /**
   * security filter chain.
   *
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

              config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
              config.setAllowedMethods(Collections.singletonList("*"));
              config.setAllowCredentials(true);
              config.setAllowedHeaders(Collections.singletonList("*"));
              config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
              config.setMaxAge(60 * 60L);
              config.setExposedHeaders(Collections.singletonList("access"));

              return config;
            }));

    http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);

    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/api/v1/logout", "/api/v1/signup", "/api/v1/tokens/reissue",
                "/oauth2/**", "/login/**", "/").permitAll()
            .requestMatchers("/v1/bringCategory", "/v1/bookCategory/**", "/v1/bringBookCategory/**",
                "/v1/bringBookFromCategory/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()
            .requestMatchers("/api/v1/question/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/answer/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/answer/**", "/api/v1/books/save")
            .hasAuthority("ADMIN")
            .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated());

    http
        .oauth2Login((oauth2) -> oauth2
            .clientRegistrationRepository(
                clientRegistrationRepository.clientRegistrationRepository())
            .userInfoEndpoint((userInfoEndpointConfig) ->
                userInfoEndpointConfig.userService(customOauth2UserService))
            .successHandler(customSuccessHandler));

    http
        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

    http
        .addFilterAt(
            new LoginFilter(authenticationManager(authenticationConfiguration),
                jwtUtil, refreshRepository, cookieUtil),
            UsernamePasswordAuthenticationFilter.class);

    http
        .addFilterBefore(
            new CustomLogoutFilter(jwtUtil, refreshRepository, cookieUtil), LogoutFilter.class);

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
