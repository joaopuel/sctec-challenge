package com.example.sctec_challenge.api.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.company.CreateCompanyDTO;
import com.example.sctec_challenge.domain.rest.GenericEntityController;
import com.example.sctec_challenge.domain.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.usecase.DeleteUseCase;
import com.example.sctec_challenge.domain.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.usecase.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.usecase.UpdateUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyController implements GenericEntityController<CreateCompanyDTO, CompanyDTO, UUID> {
    
    CreateUseCase<CreateCompanyDTO, CompanyDTO> createCompanyUseCaseImpl;
    
    @Override
    public CreateUseCase<CreateCompanyDTO, CompanyDTO> getCreateUseCase() {
        return createCompanyUseCaseImpl;
    }
    
    @Override
    public UpdateUseCase<CompanyDTO> getUpdateUseCase() {
        return null;
    }
    
    @Override
    public DeleteUseCase<UUID> getDeleteUseCase() {
        return null;
    }
    
    @Override
    public RetrieveByIdUseCase<CompanyDTO, UUID> getUniqueUseCase() {
        return null;
    }
    
    @Override
    public PageableUseCase<CompanyDTO> getPageableUseCase() {
        return null;
    }
}
