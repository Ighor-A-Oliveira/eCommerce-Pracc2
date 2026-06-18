package com.ighor.api.e_commerce.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        List<FieldError> fieldErrors,
        LocalDateTime timestamp
) {

    // Construtor para erros simples (sem lista de campos)
    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                Collections.emptyList(),
                LocalDateTime.now()
        );
    }

    // Construtor para erros de validação (com lista de campos)
    public static ErrorResponse ofValidation(HttpStatus status, List<FieldError> fieldErrors) {
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                "Erro de validação nos campos",
                fieldErrors,
                LocalDateTime.now()
        );
    }

    public record FieldError(String field, String message) {}
}