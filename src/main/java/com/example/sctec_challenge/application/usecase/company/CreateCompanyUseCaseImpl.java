package com.example.sctec_challenge.application.usecase.company;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.company.CreateCompanyDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.SaveGateway;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.usecase.CreatefNotExistsUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CreateCompanyUseCaseImpl implements CreateUseCase<CreateCompanyDTO, CompanyDTO> {
    
    CustomMapper customMapper;
    GenericMapper<CompanyModel, CompanyDTO> ownerDTOGenericMapper;
    SaveGateway<CompanyModel> saveCompanyEntityGateway;
    CreatefNotExistsUseCase<OwnerDTO> createOwnerIfNotExistsUseCase;
    
    @Override
    public CompanyDTO execute(CreateCompanyDTO dto) {
        createOwnerIfNotExistsUseCase.execute(dto.owner());
        var model = customMapper.map(dto, CompanyModel.class);
        model = saveCompanyEntityGateway.execute(model);
        return ownerDTOGenericMapper.map(model);
    }
    
}
