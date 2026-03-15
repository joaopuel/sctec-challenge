package com.example.sctec_challenge.application.usecase.owner;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.domain.gateway.PageableGateway;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PageableOwnerUseCaseImpl implements PageableUseCase<OwnerDTO> {

    PageableGateway<OwnerModel> pageableOwnerEntityGateway;
    GenericMapper<OwnerModel, OwnerDTO> ownerDTOMapper;

    @Override
    public PageDTO<OwnerDTO> execute(int page, int size) {
        return ownerDTOMapper.map(pageableOwnerEntityGateway.execute(page, size));
    }
}
