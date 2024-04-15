package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.InvalidFormatException;

public class UserNotExistException extends InvalidFormatException {

  public UserNotExistException() {
    super(ErrorCode.USER_NOT_EXIST);
  }

  public UserNotExistException(ErrorCode errorCode) {
    super(errorCode);
  }
}
