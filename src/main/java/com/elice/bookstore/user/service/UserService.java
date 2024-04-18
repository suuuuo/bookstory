package com.elice.bookstore.user.service;

import com.elice.bookstore.config.exception.domain.user.UserDuplicatedEmailException;
import com.elice.bookstore.config.exception.domain.user.UserEmptyPasswordException;
import com.elice.bookstore.config.exception.domain.user.UserNotAuthorizedException;
import com.elice.bookstore.config.exception.domain.user.UserNotExistException;
import com.elice.bookstore.config.exception.domain.user.UserNotMatchPasswordException;
import com.elice.bookstore.config.exception.domain.user.UserShortPasswordException;
import com.elice.bookstore.config.security.authentication.user.CustomUserDetails;
import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.*;
import com.elice.bookstore.user.mapper.UserMapper;
import com.elice.bookstore.user.repository.UserRepository;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

  public ResponseLookupUser lookup(String separator) {

    CustomSecurityContext sc = getCustomSecurityContext();

    User user = null;
    if (sc.auth().equals("USER")) {
      if (!separator.equals("me")) {
        throw new UserNotAuthorizedException();
      }
      user = userRepository.findById(sc.id()).orElseThrow(
          UserNotExistException::new
      );
    } else if (sc.auth().equals("ADMIN")) {
      user = userRepository.findById(sc.id()).orElseThrow(
          UserNotExistException::new
      );
    }

    return userMapper.UserToResponseLookupUser(user);
  }

  private CustomSecurityContext getCustomSecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    String auth = iterator.next().getAuthority();

    long id = Long.parseLong(customUserDetails.getId());

    return new CustomSecurityContext(id, auth);
  }

  public Page<ResponseLookupUserByAdmin> findAllUsers(int page, int size) {
    Page<User> users = userRepository.findAll(PageRequest.of(page, size));

    return userMapper.UserToResponseLookupUserByAdmin(users);
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
  /**
   * modify user.

   * @param separator two cases {me} - Role:USER, {1, 2, 3, 4, ...} - Role:ADMIN.
   * @param requestModifyUser .
   * @return user info.
   */
  @Transactional
  public ResponseLookupUser modify(String separator, RequestModifyUser requestModifyUser) {

    CustomSecurityContext sc = getCustomSecurityContext();

    User user = null;
    if (sc.auth().equals("USER")) {
      if (!separator.equals("me")) {
        throw new UserNotAuthorizedException();
      }
      user = userRepository.findByIdAndIsExist(sc.id(), true).orElseThrow(
          UserNotExistException::new
      );
    } else if (sc.auth().equals("ADMIN")) {
      user = userRepository.findByIdAndIsExist(Long.parseLong(separator), true).orElseThrow(
          UserNotExistException::new
      );
    }

    assert user != null;
    user.modifyUser(requestModifyUser);

    return userMapper.UserToResponseLookupUser(user);
  }

  /**
   * delete user.

   * @param separator .
   * @param requestDeleteUser .
   */
  @Transactional
  public void delete(String separator, RequestDeleteUser requestDeleteUser) {

    CustomSecurityContext sc = getCustomSecurityContext();

    System.out.println("requestDeleteUser = " + requestDeleteUser.toString());

    User user = null;
    if (sc.auth().equals("USER")) {
      if (!separator.equals("me")) {
        throw new UserNotAuthorizedException();
      }
      user = userRepository.findByIdAndIsExist(sc.id(), true).orElseThrow(
          UserNotExistException::new
      );
    } else if (sc.auth().equals("ADMIN")) {
      user = userRepository.findByIdAndIsExist(Long.parseLong(separator), true).orElseThrow(
          UserNotExistException::new
      );
    }

    assert user != null;

    if (sc.auth().equals("USER") && !bcryptPasswordEncoder.matches(requestDeleteUser.password(), user.getPassword())) {
      throw new UserNotMatchPasswordException();
    }

    if (sc.auth().equals("ADMIN")) {
      User admin = userRepository.findByIdAndIsExist(sc.id(), true).orElseThrow(
          UserNotExistException::new
      );
      if (!bcryptPasswordEncoder.matches(requestDeleteUser.password(), admin.getPassword())){
        throw new UserNotMatchPasswordException();
      }
    }

    user.deleteUser();
  }
}