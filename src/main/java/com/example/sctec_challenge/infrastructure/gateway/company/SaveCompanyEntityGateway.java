package com.example.sctec_challenge.infrastructure.gateway.company;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.exception.ServiceException;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.SaveGateway;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.infrastructure.persistence.entities.CompanyEntity;
import com.example.sctec_challenge.infrastructure.persistence.repositories.CompanyRepository;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SaveCompanyEntityGateway implements SaveGateway<CompanyModel> {
    
    CustomMapper customMapper;
    OwnerRepository ownerRepository;
    CompanyRepository companyRepository;
    
    @Override
    public CompanyModel execute(CompanyModel model) {
        try {
            var entity = customMapper.map(model, CompanyEntity.class);
            entity.setOwner(ownerRepository.getReferenceById(model.getOwner().getId()));
            entity = companyRepository.save(entity);
            return customMapper.map(entity, CompanyModel.class);
        } catch (DataIntegrityViolationException e) {
            String message = handleErrorMessage(e.getMessage());
            throw new ServiceException(message, HttpStatus.CONFLICT);
        }
    }
    
    private static String handleErrorMessage(String originalMessage) {
        String customMessage = "A data integrity violation occurred";
        
        if (originalMessage != null) {
            if (originalMessage.contains("CNPJ") || originalMessage.toUpperCase().contains("OWNER.CNPJ")) {
                customMessage = "CNPJ already registered. Please use a different CNPJ.";
            } else if (originalMessage.contains("EMAIL") || originalMessage.toUpperCase().contains("OWNER.EMAIL")) {
                customMessage = "Email already registered. Please use a different email.";
            }
        }
        return customMessage;
    }
    
}
