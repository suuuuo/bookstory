package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.AccessDeniedException;

public class UserNotAuthorizedException extends AccessDeniedException {
  public UserNotAuthorizedException() {
    super(ErrorCode.USER_NOT_AUTHORIZED);
  }

  public UserNotAuthorizedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
