package com.example.sctec_challenge.application.usecase.company;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.domain.exception.ServiceException;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.ports.gateway.RetrieveByIdGateway;
import com.example.sctec_challenge.domain.ports.usecase.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RetrieveByIdCompanyUseCaseImpl implements RetrieveByIdUseCase<CompanyDTO, UUID> {

    RetrieveByIdGateway<CompanyModel, UUID> retrieveByIdCompanyEntityGateway;
    GenericMapper<CompanyModel, CompanyDTO> companyDTOMapper;

    @Override
    public CompanyDTO execute(UUID id) {
        return retrieveByIdCompanyEntityGateway.execute(id)
                .map(companyDTOMapper::map)
                .orElseThrow(() -> new ServiceException("Company not found with id: " + id, HttpStatus.NOT_FOUND));
    }
    
}
