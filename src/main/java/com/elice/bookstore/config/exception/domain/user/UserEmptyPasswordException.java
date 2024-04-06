package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.InvalidFormatException;

/**
 * check user password is empty.
 */
public class UserEmptyPasswordException extends InvalidFormatException {

  public UserEmptyPasswordException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserEmptyPasswordException() {
    super(ErrorCode.USER_EMPTY_PASSWORD);
  }
}
