package com.example.sctec_challenge.application.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CompanyDTOMapper implements GenericMapper<CompanyModel, CompanyDTO> {
    
    GenericMapper<OwnerModel, OwnerDTO> ownerDTOMapper;
    
    @Override
    public CompanyDTO map(CompanyModel source) {
        Objects.requireNonNull(source, "OwnerModel object cannot be null");
        
        return new CompanyDTO(
                source.getId(),
                source.getName(),
                ownerDTOMapper.map(source.getOwner()),
                source.getMunicipality(),
                source.getCnpj(),
                source.getBusinessSegment(),
                source.getEmail(),
                source.getPhone(),
                source.getFoundationDate(),
                source.getIsActive()
        );
    }
}
