package com.elice.bookstore.config.security;

import com.elice.bookstore.config.security.filter.LoginFilter;
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

/**
 * Global Security Config.
 */
@Configuration
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;

  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
    this.authenticationConfiguration = authenticationConfiguration;
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
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);

    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/api/v1/login", "/", "/api/v1/signup").permitAll()
            .requestMatchers("/api/v1/admin").hasRole("ADMIN")
            .anyRequest().authenticated());

    http
        .addFilterAt(
            new LoginFilter(authenticationManager(authenticationConfiguration)),
            UsernamePasswordAuthenticationFilter.class);

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
