package com.example.sctec_challenge.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.BindErrorUtils;

import com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO;
import com.example.sctec_challenge.application.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCategoryDTO> handlerBadRequest(MethodArgumentNotValidException ex) {
        return ResponseEntity //
                .status(HttpStatus.BAD_REQUEST) //
                .body(new ErrorCategoryDTO("INVALID_PARAMETER", BindErrorUtils.resolveAndJoin(ex.getFieldErrors())));
    }
    
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorCategoryDTO> handlerServiceException(ServiceException ex) {
        return ResponseEntity //
                .status(ex.getCategory()) //
                .body(new ErrorCategoryDTO(ex.getCategory().name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCategoryDTO> handleGeneric(Exception ex) {
        log.error("An unexpected error occurred: ", ex);
        return ResponseEntity //
                .status(HttpStatus.INTERNAL_SERVER_ERROR) //
                .body(new ErrorCategoryDTO("INTERNAL_ERROR", "An unexpected error occurred. Please try again later."));
    }
}
