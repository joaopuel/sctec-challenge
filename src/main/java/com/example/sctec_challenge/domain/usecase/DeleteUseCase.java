package com.example.sctec_challenge.domain.usecase;

/**
 * Contract for application use cases that remove entities by identifier.
 *
 * @param <ID> identifier type accepted by the use case
 */
public interface DeleteUseCase<ID> {

    /**
     * Deletes the entity referenced by the provided identifier.
     *
     * @param id identifier of the entity to delete
     */
    void execute(ID id);
}
