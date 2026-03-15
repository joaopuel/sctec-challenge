package com.example.sctec_challenge.application.usecase.company;

import org.springframework.stereotype.Service;

import com.example.sctec_challenge.application.dto.company.CompanyDTO;
import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.domain.model.CompanyModel;
import com.example.sctec_challenge.domain.ports.gateway.PageableGateway;
import com.example.sctec_challenge.domain.ports.usecase.PageableUseCase;
import com.example.sctec_challenge.domain.utils.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PageableCompanyUseCaseImpl implements PageableUseCase<CompanyDTO> {

    PageableGateway<CompanyModel> pageableCompanyEntityGateway;
    GenericMapper<CompanyModel, CompanyDTO> companyDTOMapper;

    @Override
    public PageDTO<CompanyDTO> execute(int page, int size) {
        return companyDTOMapper.map(pageableCompanyEntityGateway.execute(page, size));
    }
}
