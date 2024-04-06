package com.elice.bookstore.config.exception.hierarchy.common;

import com.elice.bookstore.config.exception.global.ErrorCode;
import com.elice.bookstore.config.exception.hierarchy.CustomBusinessException;

/**
 * Exception middle hierarchy.
 */
public class InvalidFormatException extends CustomBusinessException {

  public InvalidFormatException(ErrorCode errorCode) {
    super(errorCode);
  }
}
