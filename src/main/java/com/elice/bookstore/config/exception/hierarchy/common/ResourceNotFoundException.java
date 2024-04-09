package com.elice.bookstore.config.exception.hierarchy.common;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.CustomBusinessException;

/**
 * Exception middle hierarchy.
 */
public class ResourceNotFoundException extends CustomBusinessException {

  public ResourceNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}

