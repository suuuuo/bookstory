package com.elice.bookstore.user.service;

import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.mapper.UserMapper;
import com.elice.bookstore.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * userService [signup].
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  private final BCryptPasswordEncoder bcryptPasswordEncoder;

  /**
   * userService dependency.

   * @param userRepository .
   * @param userMapper User to ResponseRegisterUser.
   * @param bcryptPasswordEncoder encoding password.
   */
  public UserService(UserRepository userRepository, UserMapper userMapper,
                     BCryptPasswordEncoder bcryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.bcryptPasswordEncoder = bcryptPasswordEncoder;
  }

  /**
   * signUp.

   * @param requestRegisterUser registerForm.
   * @return ResponseRegisterUser.
   */
  public ResponseRegisterUser signUp(RequestRegisterUser requestRegisterUser) {

    if (userRepository.existsByUserIdAndIsExist(requestRegisterUser.userId(), true)) {
      throw new IllegalArgumentException("The userId already exists.");
    }

    if (!requestRegisterUser.password().equals(requestRegisterUser.passwordCheck())) {
      throw new IllegalArgumentException("The password does not match.");
    }

    if (requestRegisterUser.password().isEmpty()) {
      throw new IllegalArgumentException("The password does not empty.");
    }

    if (requestRegisterUser.password().length() < 4) {
      throw new IllegalArgumentException("The password must be at least 4 characters.");
    }

    User user = new User(requestRegisterUser.userName(),
        requestRegisterUser.userId(),
        bcryptPasswordEncoder.encode(requestRegisterUser.password()),
        requestRegisterUser.dateOfBirth(),
        requestRegisterUser.email(),
        requestRegisterUser.phoneNumber(),
        requestRegisterUser.address(),
        0L, Role.USER, true);

    User savedUser = userRepository.save(user);

    return userMapper.UserToResponseRegisterUser(savedUser);
  }
}
