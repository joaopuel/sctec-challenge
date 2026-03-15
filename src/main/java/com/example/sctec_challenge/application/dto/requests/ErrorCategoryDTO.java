package com.example.sctec_challenge.application.dto.requests;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Armazena dados de erro para respostas de falha da API")
public record ErrorCategoryDTO(

        @Schema(description = "Código de erro específico para identificar o tipo de falha")
        String code,

        @Schema(description = "Mensagem de erro detalhada explicando a falha")
        String message,

        @Schema(description = "Timestamp indicando quando o erro ocorreu")
        LocalDateTime timestamp) {

    public ErrorCategoryDTO(String code, String message) {
        this(code, message, LocalDateTime.now());
    }

}
