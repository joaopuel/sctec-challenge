package com.example.sctec_challenge.infrastructure.gateway.owner;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.domain.ports.gateway.ExistsByGateway;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ExistsByCpfOwnerEntityGateway implements ExistsByGateway<String> {
    
    OwnerRepository ownerRepository;
    
    @Override
    public boolean execute(String cpf) {
        return ownerRepository.existsByCpf(cpf);
    }
}
