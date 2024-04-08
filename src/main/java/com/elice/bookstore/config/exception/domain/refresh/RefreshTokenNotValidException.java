package com.elice.bookstore.config.exception.domain.refresh;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.AccessDeniedException;

/**
 * Refresh token not valid exception.
 */
public class RefreshTokenNotValidException extends AccessDeniedException {
  public RefreshTokenNotValidException(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshTokenNotValidException() {
    super(ErrorCode.REFRESH_NOT_VALID);
  }
}
