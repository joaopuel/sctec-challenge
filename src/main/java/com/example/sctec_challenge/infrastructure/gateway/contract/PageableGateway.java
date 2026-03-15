package com.example.sctec_challenge.infrastructure.gateway.contract;

import com.example.sctec_challenge.domain.model.PageModel;

/**
 * Read gateway that retrieves paged entities without filters.
 *
 * @param <D> type of the entity model
 */
@FunctionalInterface
public interface PageableGateway<D> {

    /**
     * Fetches a page of entities.
     *
     * @param page zero-based page index
     * @param size amount of entities per page
     * @return page model describing the slice
     */
    PageModel<D> execute(Integer page, Integer size);
}
