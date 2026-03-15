package com.example.sctec_challenge.infrastructure.gateway.company;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.RetrieveByIdGateway;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.infrastructure.persistence.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RetrieveByIdCompanyEntityGateway implements RetrieveByIdGateway<CompanyModel, UUID> {
    
    CustomMapper customMapper;
    CompanyRepository companyRepository;
    
    @Override
    public Optional<CompanyModel> execute(UUID id) {
        return companyRepository.findById(id)
                .map(entity -> customMapper.map(entity, CompanyModel.class));
    }
}
