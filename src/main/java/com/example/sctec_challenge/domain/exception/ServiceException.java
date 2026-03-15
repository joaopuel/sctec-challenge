package com.example.sctec_challenge.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private HttpStatus category;

    public ServiceException(String message, HttpStatus category) {
        super(message);
        this.category = category;
    }
    
    public ServiceException(String message, HttpStatus category, Throwable cause) {
        super(message, cause);
        this.category = category;
    }

}
