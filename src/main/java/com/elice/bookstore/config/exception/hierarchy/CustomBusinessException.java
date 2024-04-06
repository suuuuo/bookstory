package com.elice.bookstore.config.exception.hierarchy;

import com.elice.bookstore.config.exception.global.ErrorCode;
import lombok.Getter;

/**
 * Top-level business exception.
 */
@Getter
public class CustomBusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomBusinessException(ErrorCode errorCode) {
    super(errorCode.getMsg());
    this.errorCode = errorCode;
  }
}
