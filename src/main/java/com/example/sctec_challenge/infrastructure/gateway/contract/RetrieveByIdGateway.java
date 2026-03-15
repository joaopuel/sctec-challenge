package com.example.sctec_challenge.infrastructure.gateway.contract;

import java.util.Optional;

/**
 * Read gateway that resolves a single entity by identifier.
 *
 * @param <D>  type of the entity model
 * @param <ID> identifier type
 */
@FunctionalInterface
public interface RetrieveByIdGateway<D, ID> {

    /**
     * Attempts to find an entity for the given identifier.
     *
     * @param id identifier to search for
     * @return optional entity when present
     */
    Optional<D> execute(ID id);
}
