package com.ighor.api.e_commerce.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception){
        return ErrorResponse.of(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    //Quando o cliente enviou uma requisição inválida
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CartEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCartEmptyException(CartEmptyException exception){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateResourceException(DuplicateResourceException exception){
        return ErrorResponse.of(HttpStatus.CONFLICT, exception.getMessage());
    }


    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientStockException(InsufficientStockException exception){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    // Erros de validação em @RequestBody (ex: @Valid em DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        //Preparando uma lista com os detalhes do erro
        List<ErrorResponse.FieldError> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                //cada erro fara parte de uma lista do objeto FieldError dentro de ErrorResponse
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        //retornando a exception tratada
        return ErrorResponse.ofValidation(HttpStatus.BAD_REQUEST, fieldErrors);
    }

    // Erros de validação em @RequestParam ou @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {

        List<ErrorResponse.FieldError> fieldErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.FieldError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();
        return ErrorResponse.ofValidation(HttpStatus.BAD_REQUEST, fieldErrors);
    }

    // Erros genéricos não mapeados
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception exception) {
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }
}
