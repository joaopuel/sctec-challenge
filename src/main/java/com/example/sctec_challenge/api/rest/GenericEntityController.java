package com.example.sctec_challenge.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.application.dto.pageable.PaginationDTO;
import com.example.sctec_challenge.application.usecase.contract.CreateUseCase;
import com.example.sctec_challenge.application.usecase.contract.PageableUseCase;
import com.example.sctec_challenge.application.usecase.contract.RetrieveByIdUseCase;
import jakarta.validation.Valid;
import lombok.NonNull;

/**
 * Interface that defines a generic REST controller for managing entities, providing default implementations for common CRUD operations.
 *
 * @param <C> the type that represents the resource to be created
 * @param <D> the inbound payload type containing creation data
 * @param <ID>  the type of the resource identifier
 */
public interface GenericEntityController<C, D, ID> {
    
    CreateUseCase<C, D> getCreateUseCase();
    RetrieveByIdUseCase<D, ID> getUniqueUseCase();
    PageableUseCase<D> getPageableUseCase();
    
    @PostMapping
    default ResponseEntity<D> create(@RequestBody @Valid C request) {
        D response = getCreateUseCase().execute(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    default ResponseEntity<D> findById(@PathVariable @NonNull @Valid ID id) {
        D response = getUniqueUseCase().execute(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    default ResponseEntity<PageDTO<D>> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        PageDTO<D> response = getPageableUseCase().execute(page, size);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping(value = "/all")
    default ResponseEntity<PageDTO<D>> findAll(@RequestBody @Valid PaginationDTO request) {
        PageDTO<D> response = getPageableUseCase().execute(request.page(), request.size());
        return ResponseEntity.ok(response);
    }
}
