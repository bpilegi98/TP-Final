package com.utn.TP_Final.controller.web;

import com.utn.TP_Final.dto.ErrorDto;
import com.utn.TP_Final.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorDto handleLoginException(InvalidLoginException exception)
    {
        return new ErrorDto(1,"Couldn't login");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorDto handleValidationException(ValidationException exception)
    {
        return new ErrorDto(2, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotExistsException.class)
    public ErrorDto handleUserNotExists()
    {
        return new ErrorDto(3, "User doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorDto handlerUserAlreadyExists()
    {
        return new ErrorDto(4, "User already exists");
    }

}
