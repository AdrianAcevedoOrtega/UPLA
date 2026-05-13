package com.example.upla.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UPLAExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> lanzarException(RuntimeException exception) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje(exception.getMessage())
                .estado(HttpStatus.BAD_REQUEST.value()) // Código 400
                .build();


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
