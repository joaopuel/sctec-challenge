package com.example.sctec_challenge.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.company.CreateCompanyDTO;
import com.example.sctec_challenge.domain.ports.rest.GenericEntityController;
import com.example.sctec_challenge.domain.ports.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.ports.usecase.DeleteUseCase;
import com.example.sctec_challenge.domain.ports.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.ports.usecase.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.ports.usecase.UpdateUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Empresas", description = "API para gerenciar empresas")
public class CompanyController implements GenericEntityController<CreateCompanyDTO, CompanyDTO, UUID> {
    
    CreateUseCase<CreateCompanyDTO, CompanyDTO> createCompanyUseCaseImpl;
    RetrieveByIdUseCase<CompanyDTO, UUID> retrieveCompanyByIdUseCaseImpl;
    PageableUseCase<CompanyDTO> pageableCompanyUseCaseImpl;
    DeleteUseCase<UUID> deleteCompanyUseCaseImpl;
    UpdateUseCase<CompanyDTO> updateCompanyUseCaseImpl;
    
    @Override
    public CreateUseCase<CreateCompanyDTO, CompanyDTO> getCreateUseCase() {
        return createCompanyUseCaseImpl;
    }
    
    @Override
    public UpdateUseCase<CompanyDTO> getUpdateUseCase() {
        return updateCompanyUseCaseImpl;
    }
    
    @Override
    public DeleteUseCase<UUID> getDeleteUseCase() {
        return deleteCompanyUseCaseImpl;
    }
    
    @Override
    public RetrieveByIdUseCase<CompanyDTO, UUID> getUniqueUseCase() {
        return retrieveCompanyByIdUseCaseImpl;
    }
    
    @Override
    public PageableUseCase<CompanyDTO> getPageableUseCase() {
        return pageableCompanyUseCaseImpl;
    }
}
