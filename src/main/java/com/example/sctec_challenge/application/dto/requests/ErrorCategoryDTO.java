package com.example.sctec_challenge.application.dto.requests;

import java.time.LocalDateTime;

public record ErrorCategoryDTO(

        String code,

        String message,

        LocalDateTime timestamp) {

    public ErrorCategoryDTO(String code, String message) {
        this(code, message, LocalDateTime.now());
    }

}
