package com.collebera.librarysystem.exception;

import com.collebera.librarysystem.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponseDTO> handleBadRequestException(RuntimeException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotfoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleNotFoundException(RuntimeException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(),HttpStatus.NOT_FOUND.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}