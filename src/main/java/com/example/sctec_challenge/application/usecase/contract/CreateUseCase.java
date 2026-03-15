package com.example.sctec_challenge.application.usecase.contract;

import jakarta.validation.Valid;

/**
 * Contract for application use cases that creates a new resource.
 *
 * @param <C> the type that represents the resource to be created
 * @param <D> the inbound payload type containing creation data
 */
public interface CreateUseCase<C, D>  {
    
    /**
     * Persists a new resource using the provided payload.
     *
     * @param value payload describing the resource to create
     * @return the created resource representation
     */
    D execute(@Valid C value);
}
