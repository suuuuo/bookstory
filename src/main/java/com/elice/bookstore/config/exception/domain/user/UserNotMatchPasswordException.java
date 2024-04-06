package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.InvalidFormatException;

/**
 * check user password match.
 */
public class UserNotMatchPasswordException extends InvalidFormatException {

  public UserNotMatchPasswordException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserNotMatchPasswordException() {
    super(ErrorCode.USER_NOT_MATCH_PASSWORD);
  }
}
