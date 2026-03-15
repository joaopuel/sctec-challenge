package com.example.sctec_challenge.domain.usecase;

import com.example.sctec_challenge.application.dto.pageable.PageDTO;

/**
 * Describes use cases that expose paginated read operations without filters.
 *
 * @param <D> type of the elements returned inside each page
 */
public interface PageableUseCase<D> {

    /**
     * Retrieves a page of resources according to the requested slice.
     *
     * @param page zero-based page index
     * @param size number of elements in the page
     * @return page model containing the requested slice
     */
    PageDTO<D> execute(int page, int size);

}
