package com.example.sctec_challenge.application.usecase.owner;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.exception.ServiceException;
import com.example.sctec_challenge.application.usecase.contract.RetrieveByIdUseCase;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.gateway.contract.RetrieveByIdGateway;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RetrieveByIdOwnerUseCaseImpl implements RetrieveByIdUseCase<OwnerDTO, UUID> {

    RetrieveByIdGateway<OwnerModel, UUID> retrieveByIdOwnerEntityGateway;
    GenericMapper<OwnerModel, OwnerDTO> ownerDTOMapper;

    @Override
    public OwnerDTO execute(UUID id) {
        return retrieveByIdOwnerEntityGateway.execute(id)
                .map(ownerDTOMapper::map)
                .orElseThrow(() -> new ServiceException("Owner not found with id: " + id, HttpStatus.NOT_FOUND));
    }
    
}
