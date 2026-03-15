package com.example.sctec_challenge.infrastructure.gateway.company;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.exception.ServiceException;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.ports.gateway.SaveGateway;
import com.example.sctec_challenge.infrastructure.persistence.entities.CompanyEntity;
import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;
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
            entity.setOwner(getOwnerEntity(model.getOwner()));
            entity = companyRepository.save(entity);
            return customMapper.map(entity, CompanyModel.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException violationException && violationException.getKind() == ConstraintViolationException.ConstraintKind.UNIQUE) {
                throw new ServiceException("CNPJ already registered. Please use a different CNPJ.", HttpStatus.CONFLICT);
            }
            
            throw new ServiceException("A data integrity violation occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private OwnerEntity getOwnerEntity(OwnerModel owner) {
        if (owner.getId() != null) {
            return ownerRepository.getReferenceById(owner.getId());
        } else {
            return ownerRepository.getReferenceByCpf(owner.getCpf());
        }
    }
    
}
