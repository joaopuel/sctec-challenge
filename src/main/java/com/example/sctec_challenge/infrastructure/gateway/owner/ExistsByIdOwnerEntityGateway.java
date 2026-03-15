package com.example.sctec_challenge.infrastructure.gateway.owner;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.infrastructure.gateway.contract.ExistsByIdGateway;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ExistsByIdOwnerEntityGateway implements ExistsByIdGateway<UUID> {
    
    OwnerRepository ownerRepository;
    
    @Override
    public boolean execute(UUID id) {
        return ownerRepository.existsById(id);
    }
}
