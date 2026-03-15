package com.example.sctec_challenge.application.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    public ServiceException(String message, HttpStatus category) {
        this.category = category;
        super(message);
    }
    
    public ServiceException(String message, HttpStatus category, Throwable cause) {
        this.category = category;
        super(message, cause);
    }
    
    private HttpStatus category;

}
