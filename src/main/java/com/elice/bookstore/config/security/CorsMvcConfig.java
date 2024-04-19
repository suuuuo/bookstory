package com.elice.bookstore.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * cors config for controller.
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/**")
        .exposedHeaders("Set-Cookie")
        .allowedOrigins("http://elicebookstory.duckdns.org")
        .allowedOrigins("http://34.22.73.96");
  }
}
