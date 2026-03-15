package com.example.sctec_challenge.domain.gateway;

/**
 * Gateway contract for checking the existence of an entity by its identifier.
 *
 * @param <ID> identifier type
 */
@FunctionalInterface
public interface ExistsByIdGateway<ID> {

    /**
     * Checks if an entity with the given identifier exists.
     *
     * @param id identifier to search for
     * @return true if an entity with the given identifier exists, false otherwise
     */
    boolean execute(ID id);
}
