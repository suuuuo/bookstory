package com.elice.bookstore.user.service;

import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.mapper.UserMapper;
import com.elice.bookstore.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {

    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public ResponseRegisterUser signUp(RequestRegisterUser requestRegisterUser) {

    if (userRepository.existsByUserIdAndIsExist(requestRegisterUser.userId())) {
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
        requestRegisterUser.password(),
        requestRegisterUser.dateOfBirth(),
        requestRegisterUser.email(),
        requestRegisterUser.phoneNumber(),
        requestRegisterUser.address(),
        0L, Role.USER, true);

    User savedUser = userRepository.save(user);

    return userMapper.UserToResponseRegisterUser(user);
  }
}
