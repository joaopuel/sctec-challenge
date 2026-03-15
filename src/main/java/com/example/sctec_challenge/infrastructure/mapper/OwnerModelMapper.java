package com.example.sctec_challenge.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.model.OwnerModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OwnerModelMapper implements GenericMapper<OwnerEntity, OwnerModel> {
    
    CustomMapper customMapper;
    
    @Override
    public OwnerModel map(OwnerEntity source) {
        return customMapper.map(source, OwnerModel.class);
    }
}
