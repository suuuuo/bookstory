package com.elice.bookstore.user.controller;

import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.dto.*;
import com.elice.bookstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("/api/v1/users")
  public Page<ResponseLookupUserByAdmin> findAllUsers(
      @RequestParam(defaultValue = "0") int page) {

    System.out.println("UserController.findAllUsers");
    return userService.findAllUsers(page, 10);
  }

  /**
   * signup.

   * @param requestRegisterUser registerForm.
   *
   * @return responseRegisterUser.
   */

  @PostMapping("/api/v1/signup")
  public ResponseEntity<ResponseRegisterUser> signup(
      @Valid @RequestBody RequestRegisterUser requestRegisterUser) {

    ResponseRegisterUser responseRegisterUser = userService.signUp(requestRegisterUser);

    return new ResponseEntity<>(responseRegisterUser, HttpStatus.OK);
  }

  /**
   * modify user.

   * @param id .
   * @param requestModifyUser .
   * @return user info.
   */
  @PatchMapping("/api/v1/users/{id}")
  public ResponseEntity<ResponseLookupUser> modify(
      @PathVariable String id,
      @Valid @RequestBody RequestModifyUser requestModifyUser) {

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
      @Valid @RequestBody RequestDeleteUser requestDeleteUser
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