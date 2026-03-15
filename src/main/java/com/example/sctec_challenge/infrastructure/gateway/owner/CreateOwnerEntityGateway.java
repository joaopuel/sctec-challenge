package com.example.sctec_challenge.infrastructure.gateway.owner;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CreateOwnerEntityGateway {
    
    CustomMapper customMapper;
    OwnerRepository ownerRepository;
    
    public OwnerModel execute(OwnerModel owner) {
        var entity = customMapper.map(owner, OwnerEntity.class);
        entity = ownerRepository.save(entity);
        return customMapper.map(entity, OwnerModel.class);
    }

}
