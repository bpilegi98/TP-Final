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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TelephoneLineNotExistsException.class)
    public ErrorDto handlerTelephoneLineNotExists()
    {
        return new ErrorDto(5, "Telephone line doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TelephoneLineAlreadyExistsException.class)
    public ErrorDto hanlderTelephoneLineAlreadyExists()
    {
        return new ErrorDto(6, "Telephone line already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateNotExistsException.class)
    public ErrorDto handlerDateNotExists()
    {
        return new ErrorDto(7, "Date doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotExistsException.class)
    public ErrorDto handlerCityNotExists()
    {
        return new ErrorDto(8, "City doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityAlreadyExistsException.class)
    public ErrorDto handlerCityAlreadyExists()
    {
        return new ErrorDto(9, "City already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CountryNotExistsException.class)
    public ErrorDto handlerCountryNotExists()
    {
        return new ErrorDto(10, "Country doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProvinceNotExistsException.class)
    public ErrorDto handlerProvinceNotExists()
    {
        return new ErrorDto(11, "Province doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeeNotExistsException.class)
    public ErrorDto handlerFeeNotExists()
    {
        return new ErrorDto(12, "Fee doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeeAlreadyExistsException.class)
    public ErrorDto handlerFeeAlreadyExists()
    {
        return new ErrorDto(13, "Fee already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CallNotExistsException.class)
    public ErrorDto handlerCallNotExists()
    {
        return new ErrorDto(14, "Call doesn't exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvoiceNotExistsException.class)
    private ErrorDto handlerInvoiceNotExists()
    {
        return new ErrorDto(15, "Invoice doesn't exists");
    }
}
