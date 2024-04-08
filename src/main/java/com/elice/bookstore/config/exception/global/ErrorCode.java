package com.elice.bookstore.config.exception.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Controller {HttpStatus, msg}, response for fronted {code}.
 */
@Getter
public enum ErrorCode {

  BAD_REQUEST(HttpStatus.BAD_REQUEST, "E1", "Invalid request format."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "E2", "The requested resource could not be found."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "E3", "You do not have permission to perform this request."),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E4", "Unsupported HTTP method."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E5", "Internal server error occurred."),

  USER_DUPLICATED_USER_ID(HttpStatus.BAD_REQUEST, "U1", "The user's id is duplicated."),
  USER_NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "U2", "The password is not match."),
  USER_EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "U3", "The password is empty."),
  USER_SHORT_PASSWORD(HttpStatus.BAD_REQUEST, "U4", "The password must be at least 4 characters."),

  REFRESH_NOT_FOUND_COOKIE(HttpStatus.BAD_REQUEST, "RE1", "The refresh token not found."),
  REFRESH_NOT_VALID(HttpStatus.BAD_REQUEST, "RE2", "The refresh token is not valid."),
  REFRESH_NOT_FOUND(HttpStatus.BAD_REQUEST, "RE3", "Could not be found in the refresh repository.")
  ;

  private final HttpStatus status;
  private final String code;
  private final String msg;

  ErrorCode(HttpStatus status, String code, String msg) {
    this.status = status;
    this.code = code;
    this.msg = msg;
  }
}
