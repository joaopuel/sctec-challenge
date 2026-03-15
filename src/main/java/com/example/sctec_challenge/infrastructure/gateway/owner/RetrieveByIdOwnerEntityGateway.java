package com.example.sctec_challenge.infrastructure.gateway.owner;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.RetrieveByIdGateway;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RetrieveByIdOwnerEntityGateway implements RetrieveByIdGateway<OwnerModel, UUID> {
    
    CustomMapper customMapper;
    OwnerRepository ownerRepository;
    
    @Override
    public Optional<OwnerModel> execute(UUID id) {
        return ownerRepository.findById(id)
                .map(entity -> customMapper.map(entity, OwnerModel.class));
    }
}
