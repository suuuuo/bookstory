package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.InvalidFormatException;

/**
 * user password at least 4 characters.
 */
public class UserShortPasswordException extends InvalidFormatException {

  public UserShortPasswordException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserShortPasswordException() {
    super(ErrorCode.USER_SHORT_PASSWORD);
  }
}
