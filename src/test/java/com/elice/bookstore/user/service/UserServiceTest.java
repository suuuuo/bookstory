package com.elice.bookstore.user.service;

import com.elice.bookstore.user.domain.Role;
import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.RequestRegisterUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import com.elice.bookstore.user.mapper.UserMapper;
import com.elice.bookstore.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  UserRepository userRepository;

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(userService, "userMapper", userMapper);
  }

  @DisplayName("회원가입을 한다.")
  @Test
  void signUp() {
    //given
    RequestRegisterUser registerForm = new RequestRegisterUser("user1", "userId1", "1234", "1234", LocalDate.of(2000, 1, 1), "user1@gmail.com", "010-1111-1111", null);
    User user = new User(
        registerForm.userName(),
        registerForm.userId(),
        registerForm.password(),
        registerForm.dateOfBirth(),
        registerForm.email(),
        registerForm.phoneNumber(),
        registerForm.address(),
        0L, Role.USER, true);
    when(userRepository.existsByUserIdAndIsExist(registerForm.userId())).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    //when
    ResponseRegisterUser responseRegisterUser = userService.signUp(registerForm);

    //then
    assertThat(responseRegisterUser).isNotNull();
    assertThat(responseRegisterUser.userName()).isEqualTo("user1");
  }

  @DisplayName("[실패] 유저 생성 시 비밀번호 공백은 안된다.")
  @Test
  void signUp_failByEmptyPassword() {
    RequestRegisterUser registerForm = new RequestRegisterUser("user1", "userId1", "", "", LocalDate.of(2000, 1, 1), "user1@gmail.com", "010-1111-1111", null);
    User user = new User(
        registerForm.userName(),
        registerForm.userId(),
        registerForm.password(),
        registerForm.dateOfBirth(),
        registerForm.email(),
        registerForm.phoneNumber(),
        registerForm.address(),
        0L, Role.USER, true);
    when(userRepository.existsByUserIdAndIsExist(registerForm.userId())).thenReturn(false);

    //when

    //then
    assertThatThrownBy(() -> userService.signUp(registerForm))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The password does not empty.");
  }

  @DisplayName("[실패] 유저 생성 시 패스워드는 일치해야 한다.")
  @Test
  void signup_failByNotMatchPassword() {
    RequestRegisterUser registerForm = new RequestRegisterUser("user1", "userId1", "1234", "7890", LocalDate.of(2000, 1, 1), "user1@gmail.com", "010-1111-1111", null);
    User user = new User(
        registerForm.userName(),
        registerForm.userId(),
        registerForm.password(),
        registerForm.dateOfBirth(),
        registerForm.email(),
        registerForm.phoneNumber(),
        registerForm.address(),
        0L, Role.USER, true);
    when(userRepository.existsByUserIdAndIsExist(registerForm.userId())).thenReturn(false);

    //when

    //then
    assertThatThrownBy(() -> userService.signUp(registerForm))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The password does not match.");
  }

  @DisplayName("[실패] 유저 생성 시 패스워드는 최소 4글자 이상이어야 한다.")
  @Test
  void signup_failByAtLeast4Character() {
    RequestRegisterUser registerForm = new RequestRegisterUser("user1", "userId1", "123", "123", LocalDate.of(2000, 1, 1), "user1@gmail.com", "010-1111-1111", null);
    User user = new User(
        registerForm.userName(),
        registerForm.userId(),
        registerForm.password(),
        registerForm.dateOfBirth(),
        registerForm.email(),
        registerForm.phoneNumber(),
        registerForm.address(),
        0L, Role.USER, true);
    when(userRepository.existsByUserIdAndIsExist(registerForm.userId())).thenReturn(false);

    //when

    //then
    assertThatThrownBy(() -> userService.signUp(registerForm))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The password must be at least 4 characters.");
  }
}