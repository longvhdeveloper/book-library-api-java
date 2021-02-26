package com.vlong.booklibrary.api.user.exception;

public class UserConflictException extends Exception {
    private String message;

    public UserConflictException(String message) {
        super(message);
    }
}
