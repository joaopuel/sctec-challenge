package com.example.sctec_challenge.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.usecase.contract.CreateUseCase;
import com.example.sctec_challenge.application.usecase.contract.DeleteUseCase;
import com.example.sctec_challenge.application.usecase.contract.PageableUseCase;
import com.example.sctec_challenge.application.usecase.contract.RetrieveByIdUseCase;
import com.example.sctec_challenge.application.usecase.contract.UpdateUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
