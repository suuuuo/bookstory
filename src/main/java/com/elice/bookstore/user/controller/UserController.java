package com.elice.bookstore.user.controller;

import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.dto.*;
import com.elice.bookstore.user.service.UserService;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/api/v1/users/me")
  public ResponseEntity<ResponseLookupUser> lookup() {

    ResponseLookupUser responseLookupUser = userService.lookup();

    return new ResponseEntity<>(responseLookupUser, HttpStatus.OK);
  }

  @PutMapping("/api/v1/users/me")
  public ResponseEntity<ResponseLookupUser> modify(
      @RequestBody RequestModifyUser requestModifyUser) {

    ResponseLookupUser responseLookupUser = userService.modify(requestModifyUser);

    return new ResponseEntity<>(responseLookupUser, HttpStatus.OK);
  }

  @DeleteMapping("/api/v1/users/me")
  public ResponseEntity<Void> delete(
      @RequestBody RequestDeleteUser requestDeleteUser
  ) {

    userService.delete(requestDeleteUser);

    return new ResponseEntity<>(HttpStatus.OK);
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

    GrantedAuthority auth2 = customUserDetails.getAuthorities().iterator().next();

    log.info("id: {}", id);
    log.info("auth.getAuthority: {}", auth.getAuthority());
    log.info("auth: ", auth2);

    return "test!!!!!!!!!";
  }
}
