package com.example.sctec_challenge.domain.ports.usecase;

/**
 * Contract for application use case that creates an resource if it does not exist.
 *
 * @param <D> the type of the resource to be created
 */
public interface CreatefNotExistsUseCase<D> {
    
    void execute(D value);
}
