package vn.vlong.booklibrary.api.shared.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.vlong.booklibrary.api.shared.controller.response.Error;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;
import vn.vlong.booklibrary.api.user.exception.UserIsNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(UserCreateIsExistException.class)
  public ResponseEntity<Error> handleUserCreateIsExistException(UserCreateIsExistException ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(4001)
        .message("Request is not valid")
        .detail(ex.getMessage())
        .build());
  }

  @ExceptionHandler(UserIsNotFoundException.class)
  public ResponseEntity<Error> handleUserIsNotFoundException(UserIsNotFoundException ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(4001)
        .message("Request is not valid")
        .detail(ex.getMessage())
        .build());
  }

  @ExceptionHandler(ActiveCodeIsNotMatchException.class)
  public ResponseEntity<Error> handleActiveCodeIsNotMatchException(
      ActiveCodeIsNotMatchException ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(4001)
        .message("Request is not valid")
        .detail(ex.getMessage())
        .build());
  }

  @ExceptionHandler(UserAlreadyActiveException.class)
  public ResponseEntity<Error> handleUserAlreadyActiveException(UserAlreadyActiveException ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(4001)
        .message("Request is not valid")
        .detail(ex.getMessage())
        .build());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Error> handleAccessDeniedException(AccessDeniedException ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(4003)
        .message("Not Authorized")
        .detail(ex.getMessage())
        .build());
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<Error> handleException(Exception ex) {
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder()
        .code(5001)
        .message("Internal Error")
        .detail(ex.getMessage())
        .build());
  }
}
