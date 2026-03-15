package com.example.sctec_challenge.infrastructure.gateway.contract;

/**
 * Abstraction for persistence gateways that create new entities.
 *
 * @param <D> type representing the entity being created
 */
@FunctionalInterface
public interface SaveGateway<D> {

    /**
     * Persists a new entity instance.
     *
     * @param value entity data to create
     * @return the persisted entity instance
     */
    D execute(D value);
}
