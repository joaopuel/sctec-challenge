package com.example.sctec_challenge.domain.ports.gateway;

/**
 * Gateway abstraction responsible for deleting entities by their identifier type.
 *
 * @param <ID> identifier type of the target entity
 */
@FunctionalInterface
public interface DeleteGateway<ID> {

    /**
     * Removes the entity identified by the provided id.
     *
     * @param id identifier of the entity to delete
     */
    void execute(ID id);
}
