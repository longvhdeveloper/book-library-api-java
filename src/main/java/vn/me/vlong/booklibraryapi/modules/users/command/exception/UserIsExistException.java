package vn.me.vlong.booklibraryapi.modules.users.command.exception;

public class UserIsExistException extends Exception {
    private String message;

    public UserIsExistException(String message) {
        super(message);
    }
}
