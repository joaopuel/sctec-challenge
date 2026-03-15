package com.example.sctec_challenge.application.dto.pageable;

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Immutable representation of a paginated slice returned by application facades.
 *
 * @param <T> type of the items held in the page contents
 * @param size number of elements per page
 * @param page zero-based index of the current page
 * @param totalElements overall aggregate of elements across all pages
 * @param totalPages total number of available pages
 * @param contents collection containing the elements for the requested page
 */
@Schema(description = "Armazena dados de uma página de resultados para consultas paginadas")
public record PageDTO<T>(

        @Schema(description = "Número de itens por página a ser retornado")
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        int size,

        @Schema(description = "Número da página a ser retornada (0 para a primeira página)")
        @Min(value = 0, message = "Page must be greater than or equal to 0") int page,

        @Schema(description = "Número total de elementos disponíveis para a consulta, considerando todas as páginas")
        @Min(value = 0, message = "Total elements must be greater than or equal to 0") long totalElements,

        @Schema(description = "Número total de páginas disponíveis para a consulta, considerando o tamanho da página e o total de elementos")
        @Min(value = 0, message = "Total pages must be greater than or equal to 0") int totalPages,

        @Schema(description = "Coleção contendo os elementos para a página solicitada")
        @NotNull(message = "Contents cannot be null") Collection<T> contents) {
    
    public static  <T> PageDTO<T> empty() {
        return new PageDTO<>(0, 0, 0, 0, java.util.Collections.emptyList());
    }

}
