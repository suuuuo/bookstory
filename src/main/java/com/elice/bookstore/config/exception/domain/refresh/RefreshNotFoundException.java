package com.elice.bookstore.config.exception.domain.refresh;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.common.ResourceNotFoundException;

/**
 * Refresh token not found in repository.
 */
public class RefreshNotFoundException extends ResourceNotFoundException {

  public RefreshNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshNotFoundException() {
    super(ErrorCode.REFRESH_NOT_FOUND);
  }
}
