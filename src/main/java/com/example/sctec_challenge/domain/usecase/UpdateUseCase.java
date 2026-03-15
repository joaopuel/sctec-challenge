package com.example.sctec_challenge.domain.usecase;

/**
 * Contract for use cases responsible for updating an existing resource.
 *
 * @param <D> type representing the resource state handled by the use case
 */
public interface UpdateUseCase<D> {

    /**
     * Updates the resource using the provided representation.
     *
     * @param value resource state carrying the changes to persist
     * @return the updated resource representation
     */
    D execute(D value);
}
