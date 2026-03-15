package com.example.sctec_challenge.application.usecase.owner;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.exception.ServiceException;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.gateway.ExistsByGateway;
import com.example.sctec_challenge.domain.gateway.SaveGateway;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.usecase.UpdateUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UpdateOwnerUseCaseImpl implements UpdateUseCase<OwnerDTO> {
    
    CustomMapper customMapper;
    GenericMapper<OwnerModel, OwnerDTO> ownerDTOGenericMapper;
    SaveGateway<OwnerModel> saveOwnerEntityGateway;
    ExistsByGateway<UUID> existsByIdOwnerEntityGateway;
    
    @Override
    public OwnerDTO execute(OwnerDTO dto) {
        if (!existsByIdOwnerEntityGateway.execute(dto.id())) {
            throw new ServiceException("Owner not found with id: " + dto.id(), HttpStatus.NOT_FOUND);
        }

        var model = customMapper.map(dto, OwnerModel.class);
        model = saveOwnerEntityGateway.execute(model);
        return ownerDTOGenericMapper.map(model);
    }
    
}
