package com.example.sctec_challenge.application.dto.pageable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Armazena dados de paginação para consultas paginadas")
public record PaginationDTO(

        @Schema(description = "Número da página a ser retornada (0 para a primeira página)")
        @Min(value = 0, message = "Size must be greater than or equal to 0")
        int page,

        @Schema(description = "Número de itens por página a ser retornado")
        @Min(value = 1, message = "Page must be greater than or equal to 1")
        int size
) {

}

