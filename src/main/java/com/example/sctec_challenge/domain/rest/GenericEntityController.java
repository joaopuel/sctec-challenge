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
        summary = "Criar um novo recurso",
        description = "Cria um novo recurso com os dados fornecidos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", 
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "409", description = "Recurso já existe (conflito)",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> create(@RequestBody @Valid C request) {
        D response = getCreateUseCase().execute(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping
    @Operation(
        summary = "Atualizar um recurso existente",
        description = "Atualiza um recurso existente com os dados fornecidos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "409", description = "Recurso já existe (conflito)",
                content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> merge(@RequestBody @Valid D request) {
        D response = getUpdateUseCase().execute(request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um recurso",
        description = "Exclui um recurso pelo seu identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso excluído com sucesso ou sem conteúdo para excluir"),
        @ApiResponse(responseCode = "400", description = "Identificador do recurso inválido",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<Void> delete(
            @Parameter(description = "Identificador único do recurso", required = true)
            @PathVariable @NonNull @Valid ID id) {
        getDeleteUseCase().execute(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar um recurso por ID",
        description = "Recupera um único recurso pelo seu identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Identificador do recurso inválido",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<D> findById(
            @Parameter(description = "Identificador único do recurso", required = true)
            @PathVariable @NonNull @Valid ID id) {
        D response = getUniqueUseCase().execute(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(
        summary = "Buscar todos os recursos (paginado) - Versão GET",
        description = "Recupera uma lista paginada de todos os recursos usando requisição GET com parâmetros de paginação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<PageDTO<D>> findAll(
            @Parameter(description = "Número da página (iniciado em 0)", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Número de itens por página", example = "10")
            @RequestParam Integer size) {
        PageDTO<D> response = getPageableUseCase().execute(page, size);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping(value = "/all")
    @Operation(
        summary = "Buscar todos os recursos (paginado) - Versão POST",
        description = "Recupera uma lista paginada de todos os recursos usando requisição POST com detalhes de paginação no corpo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recursos recuperados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos",
            content = @Content(schema = @Schema(implementation = com.example.sctec_challenge.application.dto.requests.ErrorCategoryDTO.class)))
    })
    default ResponseEntity<PageDTO<D>> findAll(@RequestBody @Valid PaginationDTO request) {
        PageDTO<D> response = getPageableUseCase().execute(request.page(), request.size());
        return ResponseEntity.ok(response);
    }
}
