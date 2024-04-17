package com.elice.bookstore.user.controller;

import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.dto.RequestDeleteUser;
import com.elice.bookstore.user.dto.RequestModifyUser;
import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseLookupUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

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
   * lookup user.

   * @param id .
   * @return user info.
   */
  @GetMapping("/api/v1/users/{id}")
  public ResponseEntity<ResponseLookupUser> lookup(
      @PathVariable String id) {

    ResponseLookupUser responseLookupUser = userService.lookup(id);

    return new ResponseEntity<>(responseLookupUser, HttpStatus.OK);
  }

  /**
   * modify user.

   * @param id .
   * @param requestModifyUser .
   * @return user info.
   */
  @PutMapping("/api/v1/users/{id}")
  public ResponseEntity<ResponseLookupUser> modify(
      @PathVariable String id,
      @RequestBody RequestModifyUser requestModifyUser) {

    ResponseLookupUser responseLookupUser = userService.modify(id, requestModifyUser);

    return new ResponseEntity<>(responseLookupUser, HttpStatus.OK);
  }

  /**
   * delete user.

   * @param id .
   * @param requestDeleteUser .
   * @return .
   */
  @DeleteMapping("/api/v1/users/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable String id,
      @RequestBody RequestDeleteUser requestDeleteUser
  ) {

    userService.delete(id, requestDeleteUser);

    return new ResponseEntity<>(HttpStatus.OK);
  }

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
