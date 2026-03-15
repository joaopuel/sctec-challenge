package com.example.sctec_challenge.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.domain.rest.GenericEntityController;
import com.example.sctec_challenge.domain.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.usecase.DeleteUseCase;
import com.example.sctec_challenge.domain.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.usecase.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.usecase.UpdateUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Owner", description = "API for managing company owners")
public class OwnerController implements GenericEntityController<CreateOwnerDTO, OwnerDTO, UUID> {
    
    CreateUseCase<CreateOwnerDTO, OwnerDTO> createOwnerUseCaseImpl;
    RetrieveByIdUseCase<OwnerDTO, UUID> retrieveOwnerByIdUseCaseImpl;
    PageableUseCase<OwnerDTO> pageableOwnerUseCaseImpl;
    DeleteUseCase<UUID> deleteOwnerUseCaseImpl;
    UpdateUseCase<OwnerDTO> updateOwnerUseCaseImpl;
    
    @Override
    public CreateUseCase<CreateOwnerDTO, OwnerDTO> getCreateUseCase() {
        return createOwnerUseCaseImpl;
    }
    
    @Override
    public UpdateUseCase<OwnerDTO> getUpdateUseCase() {
        return updateOwnerUseCaseImpl;
    }
    
    @Override
    public DeleteUseCase<UUID> getDeleteUseCase() {
        return deleteOwnerUseCaseImpl;
    }
    
    @Override
    public RetrieveByIdUseCase<OwnerDTO, UUID> getUniqueUseCase() {
        return retrieveOwnerByIdUseCaseImpl;
    }
    
    @Override
    public PageableUseCase<OwnerDTO> getPageableUseCase() {
        return pageableOwnerUseCaseImpl;
    }
}
