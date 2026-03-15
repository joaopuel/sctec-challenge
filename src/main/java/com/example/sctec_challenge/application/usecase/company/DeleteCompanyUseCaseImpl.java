package com.example.sctec_challenge.application.usecase.company;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.domain.gateway.DeleteGateway;
import com.example.sctec_challenge.domain.usecase.DeleteUseCase;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DeleteCompanyUseCaseImpl implements DeleteUseCase<UUID> {

    DeleteGateway<UUID> deleteCompanyEntityGateway;

    @Override
    public void execute(UUID id) {
        deleteCompanyEntityGateway.execute(id);
    }
    
}
