package com.elice.bookstore.config.exception.domain.refresh;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.ResourceNotFoundException;

/**
 * refresh token not included in the cookie.
 */
public class RefreshNotFoundCookieException extends ResourceNotFoundException {
  public RefreshNotFoundCookieException(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshNotFoundCookieException() {
    super(ErrorCode.REFRESH_NOT_FOUND_COOKIE);
  }
}
