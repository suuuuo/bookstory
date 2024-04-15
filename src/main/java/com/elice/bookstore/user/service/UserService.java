package com.elice.bookstore.user.service;

import com.elice.bookstore.config.exception.domain.user.*;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.*;
import com.elice.bookstore.user.mapper.UserMapper;
import com.elice.bookstore.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    if (userRepository.existsByEmailAndIsExist(requestRegisterUser.email(), true)) {
      throw new UserDuplicatedEmailException();
    }

    if (!requestRegisterUser.password().equals(requestRegisterUser.passwordCheck())) {
      throw new UserNotMatchPasswordException();
    }

    if (requestRegisterUser.password().isEmpty()) {
      throw new UserEmptyPasswordException();
    }

    if (requestRegisterUser.password().length() < 4) {
      throw new UserShortPasswordException();
    }

    User user = new User(requestRegisterUser.userName(),
        requestRegisterUser.email(),
        bcryptPasswordEncoder.encode(requestRegisterUser.password()),
        requestRegisterUser.dateOfBirth(),
        requestRegisterUser.email(),
        requestRegisterUser.phoneNumber(),
        requestRegisterUser.address(),
        0L, Role.USER, true);

    User savedUser = userRepository.save(user);

    return userMapper.UserToResponseRegisterUser(savedUser);
  }

  public ResponseLookupUser lookup() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());
    User user = userRepository.findByIdAndIsExist(id, true).orElseThrow(
        UserNotExistException::new
    );

    return userMapper.UserToResponseLookupUser(user);
  }

  @Transactional
  public ResponseLookupUser modify(RequestModifyUser requestModifyUser) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());
    User user = userRepository.findByIdAndIsExist(id, true).orElseThrow(
        UserNotExistException::new
    );

    if (!user.getEmail().equals(requestModifyUser.email()) &&
        userRepository.existsByEmailAndIsExist(requestModifyUser.email(), true)) {
      throw new UserDuplicatedEmailException();
    }

    user.modifyUser(requestModifyUser);

    return userMapper.UserToResponseLookupUser(user);
  }

  @Transactional
  public void delete(RequestDeleteUser requestDeleteUser) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    long id = Long.parseLong(customUserDetails.getId());
    User user = userRepository.findByIdAndIsExist(id, true).orElseThrow(
        UserNotExistException::new
    );

    if (!bcryptPasswordEncoder.matches(requestDeleteUser.password(), user.getPassword())) {
      throw new UserNotMatchPasswordException();
    }

    user.deleteUser();
  }
}
