package dev.sosnovsky.applications.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        return new ErrorResponse("Роль уже назначена", e.getMessage());
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
}
