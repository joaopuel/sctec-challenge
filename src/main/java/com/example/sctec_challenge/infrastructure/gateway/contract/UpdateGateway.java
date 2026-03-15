package com.example.sctec_challenge.infrastructure.gateway.contract;

/**
 * Gateway abstraction used to update existing entities.
 *
 * @param <D> type of the entity being updated
 */
@FunctionalInterface
public interface UpdateGateway<D> {

    /**
     * Applies updates to the provided entity instance.
     *
     * @param value the entity model containing the updated data
     * @return the updated entity model instance
     */
    D execute(D value);
}
