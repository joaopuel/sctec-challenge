package com.example.sctec_challenge.infrastructure.gateway.owner;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.exception.ServiceException;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.SaveGateway;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SaveOwnerEntityGateway implements SaveGateway<OwnerModel> {
    
    CustomMapper customMapper;
    OwnerRepository ownerRepository;
    
    @Override
    public OwnerModel execute(OwnerModel owner) {
        try {
            var entity = customMapper.map(owner, OwnerEntity.class);
            entity = ownerRepository.save(entity);
            return customMapper.map(entity, OwnerModel.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException violationException && violationException.getKind() == ConstraintViolationException.ConstraintKind.UNIQUE) {
                throw new ServiceException("CPF already registered. Please use a different CPF.", HttpStatus.CONFLICT);
            }
            
            throw new ServiceException("A data integrity violation occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
