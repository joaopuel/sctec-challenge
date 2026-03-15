package com.example.sctec_challenge.application.usecase.company;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.exception.ServiceException;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.ports.gateway.ExistsByGateway;
import com.example.sctec_challenge.domain.ports.gateway.SaveGateway;
import com.example.sctec_challenge.domain.ports.usecase.CreatefNotExistsUseCase;
import com.example.sctec_challenge.domain.ports.usecase.UpdateUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UpdateCompanyUseCaseImpl implements UpdateUseCase<CompanyDTO> {
    
    CustomMapper customMapper;
    GenericMapper<CompanyModel, CompanyDTO> companyDTOGenericMapper;
    SaveGateway<CompanyModel> saveCompanyEntityGateway;
    ExistsByGateway<UUID> existsByIdCompanyEntityGateway;
    CreatefNotExistsUseCase<OwnerDTO> createOwnerIfNotExistsUseCase;
    
    @Override
    public CompanyDTO execute(CompanyDTO dto) {
        if (!existsByIdCompanyEntityGateway.execute(dto.id())) {
            throw new ServiceException("Company not found with id: " + dto.id(), HttpStatus.NOT_FOUND);
        }
        
        createOwnerIfNotExistsUseCase.execute(dto.owner());
        var model = customMapper.map(dto, CompanyModel.class);
        model = saveCompanyEntityGateway.execute(model);
        return companyDTOGenericMapper.map(model);
    }
    
}
