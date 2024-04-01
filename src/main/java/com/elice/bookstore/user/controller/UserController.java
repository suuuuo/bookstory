package com.elice.bookstore.user.controller;

import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 */
@RestController
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
  public ResponseEntity<ResponseRegisterUser> signup(@RequestBody RequestRegisterUser requestRegisterUser) {
    ResponseRegisterUser responseRegisterUser = userService.signUp(requestRegisterUser);

    return new ResponseEntity<>(responseRegisterUser, HttpStatus.OK);
  }
}
