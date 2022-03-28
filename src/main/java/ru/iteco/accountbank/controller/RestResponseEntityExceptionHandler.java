package ru.iteco.accountbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.iteco.accountbank.exception.BankBookException;
import ru.iteco.accountbank.exception.NotFoundException;
import ru.iteco.accountbank.exception.ValidationException;
import ru.iteco.accountbank.model.ErrorDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<ErrorDto> handleValidationException() {
        return new ResponseEntity<ErrorDto>(ErrorDto.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorDto> handleNotFoundException() {
        return new ResponseEntity<ErrorDto>(ErrorDto.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BankBookException.class)
    protected ResponseEntity<ErrorDto> handleBankBookException(Exception exception) {
        if (exception.getMessage().contains("Счет с данной валютой уже имеется в хранилище")) {
            return new ResponseEntity<ErrorDto>(ErrorDto.CURRENCY_ERROR, HttpStatus.BAD_REQUEST);
        } else if (exception.getMessage().equals("Номер менять нельзя")) {
            return new ResponseEntity<ErrorDto>(ErrorDto.NUMBER_ERROR, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<ErrorDto>(ErrorDto.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);

        }
    }
}
