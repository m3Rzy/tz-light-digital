package ru.lightdigital.tzlightdigital.util.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lightdigital.tzlightdigital.util.exception.AccessException;
import ru.lightdigital.tzlightdigital.util.exception.BadRequestException;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(NotFoundException e) {
        return new ErrorResponse("Data not found.", HttpStatus.NOT_FOUND.value(),
                e.getDesc());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BadRequestException e) {
        return new ErrorResponse("Bad request.", HttpStatus.BAD_REQUEST.value(),
                e.getDesc());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handle(AccessException e) {
        return new ErrorResponse("Access exception.", HttpStatus.NOT_ACCEPTABLE.value(),
                e.getDesc());
    }
}
