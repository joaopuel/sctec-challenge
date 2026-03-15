package com.example.sctec_challenge.domain.usecase;

/**
 * Contract for use cases that retrieve a single resource represented by a unique identifier.
 *
 * @param <D> resource type returned by the use case
 * @param <ID> identifier type used to locate the resource
 */
public interface RetrieveByIdUseCase<D, ID> {

    /**
     * Retrieves the resource matching the provided identifier.
     *
     * @param id identifier of the resource to fetch
     * @return the matching resource instance
     */
    D execute(ID id);
}
