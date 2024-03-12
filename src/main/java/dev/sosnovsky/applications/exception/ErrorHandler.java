package dev.sosnovsky.applications.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        e.printStackTrace();
        return new ErrorResponse("Объект не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRoleAlreadyExistsException(RoleAlreadyExistsException e) {
        e.printStackTrace();
        return new ErrorResponse("Ошибка назначения роли", e.getMessage());
    }

    // todo поймать ошибку валидации данных MethodArgumentNotValidException
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return new ErrorResponse("Ошибка валидации данных", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNotCreatorException(NotCreatorException e) {
        e.printStackTrace();
        return new ErrorResponse("Ошибка доступа", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleStatusException(StatusException e) {
        e.printStackTrace();
        return new ErrorResponse("Ошибка при изменении статуса", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLoginOrPasswordException(LoginOrPasswordException e) {
        e.printStackTrace();
        return new ErrorResponse("Некорректные данные", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTokenException(TokenException e) {
        e.printStackTrace();
        return new ErrorResponse("Токен некорректен", e.getMessage());
    }
}
