package com.example.sctec_challenge.domain.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.application.dto.pageable.PaginationDTO;
import com.example.sctec_challenge.domain.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.usecase.DeleteUseCase;
import com.example.sctec_challenge.domain.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.usecase.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.usecase.UpdateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.NonNull;

/**
 * Interface that defines a generic REST controller for managing entities, providing default implementations for common CRUD operations.
 *
 * @param <C> the type that represents the resource to be created
 * @param <D> the type that represents the resource to be returned in responses
 * @param <ID>  the type of the resource identifier
 */
public interface GenericEntityController<C, D, ID> {
    
    CreateUseCase<C, D> getCreateUseCase();
    UpdateUseCase<D> getUpdateUseCase();
    DeleteUseCase<ID> getDeleteUseCase();
    RetrieveByIdUseCase<D, ID> getUniqueUseCase();
    PageableUseCase<D> getPageableUseCase();
    
    @PostMapping
    @Operation(
        summary = "Create a new resource",
        description = "Creates a new resource with the provided data"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resource created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", 
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "409", description = "Resource already exists (conflict)",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> create(@RequestBody @Valid C request) {
        D response = getCreateUseCase().execute(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping
    @Operation(
        summary = "Update an existing resource",
        description = "Updates an existing resource with the provided data"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resource updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "409", description = "Resource already exists (conflict)",
                content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> merge(@RequestBody @Valid D request) {
        D response = getUpdateUseCase().execute(request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a resource",
        description = "Deletes a resource by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resource deleted successfully or no content to delete"),
        @ApiResponse(responseCode = "400", description = "Invalid resource identifier",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<Void> delete(
            @Parameter(description = "Unique identifier of the resource", required = true)
            @PathVariable @NonNull @Valid ID id) {
        getDeleteUseCase().execute(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a resource by ID",
        description = "Retrieves a single resource by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resource retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid resource identifier",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> findById(
            @Parameter(description = "Unique identifier of the resource", required = true)
            @PathVariable @NonNull @Valid ID id) {
        D response = getUniqueUseCase().execute(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(
        summary = "Get all resources (paginated)",
        description = "Retrieves a paginated list of all resources"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<PageDTO<D>> findAll(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam Integer size) {
        PageDTO<D> response = getPageableUseCase().execute(page, size);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping(value = "/all")
    @Operation(
        summary = "Get all resources (paginated) - POST version",
        description = "Retrieves a paginated list of all resources using POST request with pagination details in the body"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resources retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<PageDTO<D>> findAll(@RequestBody @Valid PaginationDTO request) {
        PageDTO<D> response = getPageableUseCase().execute(request.page(), request.size());
        return ResponseEntity.ok(response);
    }
}
