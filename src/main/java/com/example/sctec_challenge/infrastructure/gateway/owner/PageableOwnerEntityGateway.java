package com.example.sctec_challenge.infrastructure.gateway.owner;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.model.PageModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.gateway.contract.PageableGateway;
import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;
import com.example.sctec_challenge.infrastructure.persistence.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PageableOwnerEntityGateway implements PageableGateway<OwnerModel> {
    
    GenericMapper<OwnerEntity, OwnerModel> ownerModelMapper;
    OwnerRepository ownerRepository;
    
    @Override
    public PageModel<OwnerModel> execute(Integer page, Integer size) {
        var pageRequest = PageRequest.of(page, size);
        return ownerModelMapper.map(ownerRepository.findAll(pageRequest));
        
    }
}
