package com.elice.bookstore.user.mapper;

import com.elice.bookstore.user.domain.User;
import com.elice.bookstore.user.dto.ResponseLookupUser;
import com.elice.bookstore.user.dto.ResponseRegisterUser;
import org.mapstruct.Mapper;

/**
 * user mapper.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  ResponseRegisterUser UserToResponseRegisterUser(User user);
  ResponseLookupUser UserToResponseLookupUser(User user);
}
