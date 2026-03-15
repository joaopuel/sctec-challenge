package com.example.sctec_challenge.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.example.sctec_challenge.application.utils.CustomMapper;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.persistence.entities.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CompanyModelMapper implements GenericMapper<CompanyEntity, CompanyModel> {
    
    CustomMapper customMapper;
    
    @Override
    public CompanyModel map(CompanyEntity source) {
        return customMapper.map(source, CompanyModel.class);
    }
}
