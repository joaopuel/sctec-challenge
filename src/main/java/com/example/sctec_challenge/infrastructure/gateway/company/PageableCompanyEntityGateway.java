package com.example.sctec_challenge.infrastructure.gateway.company;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.sctec_challenge.domain.gateway.PageableGateway;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.model.PageModel;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import com.example.sctec_challenge.infrastructure.persistence.entities.CompanyEntity;
import com.example.sctec_challenge.infrastructure.persistence.repositories.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PageableCompanyEntityGateway implements PageableGateway<CompanyModel> {
    
    GenericMapper<CompanyEntity, CompanyModel> companyModelMapper;
    CompanyRepository companyRepository;
    
    @Override
    public PageModel<CompanyModel> execute(Integer page, Integer size) {
        var pageRequest = PageRequest.of(page, size);
        return companyModelMapper.map(companyRepository.findAll(pageRequest));
    }
}
