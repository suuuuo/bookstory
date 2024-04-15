package com.elice.bookstore.config.security.service;

import com.elice.bookstore.config.exception.domain.user.UserNotExistException;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService.
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("loadUserByUsername, email:{}", email);
    User user = userRepository.findByEmailAndIsExist(email, true).orElseThrow(
        UserNotExistException::new
    );

    return new CustomUserDetails(user);
  }
}
