package com.example.sctec_challenge.application.usecase.owner;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.usecase.contract.CreateUseCase;
import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.gateway.owner.SaveOwnerEntityGateway;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CreateOwnerUseCaseImpl implements CreateUseCase<CreateOwnerDTO, OwnerDTO> {
    
    CustomMapper customMapper;
    GenericMapper<OwnerModel, OwnerDTO> ownerDTOGenericMapper;
    SaveOwnerEntityGateway saveOwnerEntityGateway;
    
    @Override
    public OwnerDTO execute(CreateOwnerDTO dto) {
        var model = customMapper.map(dto, OwnerModel.class);
        model = saveOwnerEntityGateway.execute(model);
        return ownerDTOGenericMapper.map(model);
    }
    
}
