package com.example.sctec_challenge.application.mapper;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.domain.utils.GenericMapper;

@Component
public class CreateOwnerDTOMapper implements GenericMapper<OwnerDTO, CreateOwnerDTO> {
    
    @Override
    public CreateOwnerDTO map(OwnerDTO source) {
        return new CreateOwnerDTO(source.name(), source.email(), source.phone(), source.cpf(), source.birthDate());
    }
}
