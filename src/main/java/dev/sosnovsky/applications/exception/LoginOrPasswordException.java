package dev.sosnovsky.applications.exception;

public class LoginOrPasswordException extends RuntimeException {
    public LoginOrPasswordException(String message) {
        super(message);
    }
}
