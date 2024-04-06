package com.elice.bookstore.config.exception.global;

import com.elice.bookstore.config.exception.hierarchy.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({
      CustomBusinessException.class
  })
  public ResponseEntity<ErrorResponse> handle(CustomBusinessException e) {
    return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), e.getErrorCode().getStatus());
  }

  /**
   * Unhandled errors are treated as server errors.

   * @param e .
   * @return .
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handle(Exception e) {
    log.error(e.getMessage());

    return new ResponseEntity<>(
        ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR),
        ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
  }
}
