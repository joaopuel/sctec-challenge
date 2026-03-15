package com.example.sctec_challenge.application.dto.pageable;

import java.util.Collection;

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
public record PageDTO<T>(

        @Min(value = 1, message = "Size must be greater than or equal to 1") int size,

        @Min(value = 0, message = "Page must be greater than or equal to 0") int page,

        @Min(value = 0, message = "Total elements must be greater than or equal to 0") long totalElements,

        @Min(value = 0, message = "Total pages must be greater than or equal to 0") int totalPages,

        @NotNull(message = "Contents cannot be null") Collection<T> contents) {
    
    public static  <T> PageDTO<T> empty() {
        return new PageDTO<>(0, 0, 0, 0, java.util.Collections.emptyList());
    }

}
