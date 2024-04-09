package dev.sosnovsky.applications.exception;

public class IncorrectPhoneException extends RuntimeException {
    public IncorrectPhoneException(String message) {
        super(message);
    }
}
