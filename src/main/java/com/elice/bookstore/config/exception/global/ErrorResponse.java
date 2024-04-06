package com.elice.bookstore.config.exception.global;

import lombok.Getter;

/**
 * Error response.
 */
@Getter
public class ErrorResponse {

  private final String code;
  private final String msg;

  public ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.msg = errorCode.getMsg();
  }

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }
}
