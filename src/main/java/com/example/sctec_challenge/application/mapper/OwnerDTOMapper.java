package com.example.sctec_challenge.application.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;

@Component
public class OwnerDTOMapper implements GenericMapper<OwnerModel, OwnerDTO> {
    
    @Override
    public OwnerDTO map(OwnerModel source) {
        Objects.requireNonNull(source, "OwnerModel object cannot be null");
        
        return new OwnerDTO(
            source.getId(),
            source.getName(),
            source.getEmail(),
            source.getPhone(),
            source.getCpf(),
            source.getBirthDate()
        );
    }
}
