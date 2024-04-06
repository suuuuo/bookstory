package com.elice.bookstore.config.exception.domain.user;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.DuplicatedException;

/**
 * check dup userId.
 */
public class UserDuplicatedUserIdException extends DuplicatedException {

  public UserDuplicatedUserIdException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserDuplicatedUserIdException() {
    super(ErrorCode.USER_DUPLICATED_USER_ID);
  }
}
