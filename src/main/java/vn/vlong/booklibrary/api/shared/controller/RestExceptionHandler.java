package vn.vlong.booklibrary.api.shared.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.vlong.booklibrary.api.shared.controller.response.Error;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;

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
}
