package com.example.sctec_challenge.application.usecase.owner;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.exception.ServiceException;
import com.example.sctec_challenge.domain.gateway.ExistsByGateway;
import com.example.sctec_challenge.domain.usecase.CreateUseCase;
import com.example.sctec_challenge.domain.usecase.CreatefNotExistsUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CreateOwnerIfNotExistsUseCaseImpl implements CreatefNotExistsUseCase<OwnerDTO> {
    
    ExistsByGateway<UUID> existsByIdOwnerEntityGateway;
    GenericMapper<OwnerDTO, CreateOwnerDTO> createOwnerDTOMapper;
    ExistsByGateway<String> existsByCpfOwnerEntityGateway;
    Validator validator;
    CreateUseCase<CreateOwnerDTO, OwnerDTO> createOwnerUseCaseImpl;
    
    @Override
    public void execute(OwnerDTO ownerDTO) {
        if (ownerDTO.id() != null) {
            if (!existsByIdOwnerEntityGateway.execute(ownerDTO.id())) {
                throw new ServiceException("Owner not found with id: " + ownerDTO.id(), HttpStatus.NOT_FOUND);
            }
            return;
        }
        
        if (ownerDTO.cpf() != null && existsByCpfOwnerEntityGateway.execute(ownerDTO.cpf())) {
            return;
        }
        
        var createDTO = createOwnerDTOMapper.map(ownerDTO);
        
        Set<ConstraintViolation<CreateOwnerDTO>> validate = validator.validate(createDTO);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
        
        createOwnerUseCaseImpl.execute(createDTO);
    }
}
