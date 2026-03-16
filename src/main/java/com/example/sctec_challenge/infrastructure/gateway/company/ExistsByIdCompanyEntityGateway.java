package com.example.sctec_challenge.infrastructure.gateway.company;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.domain.ports.gateway.ExistsByGateway;
import com.example.sctec_challenge.infrastructure.persistence.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ExistsByIdCompanyEntityGateway implements ExistsByGateway<UUID> {
    
    CompanyRepository companyRepository;
    
    @Override
    public boolean execute(UUID id) {
        return companyRepository.existsById(id);
    }
}
