package com.example.sctec_challenge.application.usecase.owner;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.usecase.contract.DeleteUseCase;
import com.example.sctec_challenge.infrastructure.gateway.contract.DeleteGateway;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DeleteUseCaseImpl implements DeleteUseCase<UUID> {

    DeleteGateway<UUID> deleteOwnerEntityGateway;

    @Override
    public void execute(UUID id) {
        deleteOwnerEntityGateway.execute(id);
    }
    
}
