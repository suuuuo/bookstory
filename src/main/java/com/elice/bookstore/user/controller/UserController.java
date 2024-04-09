package com.elice.bookstore.user.controller;

import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.service.UserService;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 */
@RestController
@Slf4j
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * signup.

   * @param requestRegisterUser registerForm.
   *
   * @return responseRegisterUser.
   */
  @PostMapping("/api/v1/signup")
  public ResponseEntity<ResponseRegisterUser> signup(
      @RequestBody RequestRegisterUser requestRegisterUser) {
    ResponseRegisterUser responseRegisterUser = userService.signUp(requestRegisterUser);

    return new ResponseEntity<>(responseRegisterUser, HttpStatus.OK);
  }

  /**
   * cors test controller.

   * @return "test"
   */
  @PostMapping("/api/v1/test")
  public String postTest() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    String id = customUserDetails.getId();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();

    log.info("id: {}", id);
    log.info("auth.getAuthority: {}", auth.getAuthority());

    return "test!!!!!!!!!";
  }
}
