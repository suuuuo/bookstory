package com.elice.bookstore.user.mapper;

import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.ResponseLookupUser;
import com.elice.bookstore.user.dto.ResponseLookupUserByAdmin;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * user mapper.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  ResponseRegisterUser UserToResponseRegisterUser(User user);
  ResponseLookupUser UserToResponseLookupUser(User user);

  @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "formatDate")
  @Mapping(target = "loginType", expression = "java(UserMapper.determineLoginType(user.getUserId(), user.getEmail()))")
  ResponseLookupUserByAdmin UserToResponseLookUpUserByAdmin(User user);

  default Page<ResponseLookupUserByAdmin> UserToResponseLookupUserByAdmin(Page<User> users) {
    return users.map(this::UserToResponseLookUpUserByAdmin);
  }

  @Named("formatDate")
  public static String formatDate(LocalDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public static String determineLoginType(String userId, String email) {
    if (userId.equals(email)) {
      return "general";
    } else {
      String[] parts = userId.split("\\s+");
      return parts[0];
    }
  }
}