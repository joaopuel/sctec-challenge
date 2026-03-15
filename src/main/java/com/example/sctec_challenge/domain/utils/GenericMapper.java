package com.example.sctec_challenge.domain.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.domain.model.PageModel;

public interface GenericMapper<S, D> {
    
    D map(S source);
    
    default List<D> map(Collection<S> source) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        
        return source.stream()
                .map(this::map)
                .toList();
    }
    
    default PageModel<D> map(Page<S> source) {
        if (source == null) {
            return PageModel.empty();
        }
        
        return PageModel.<D>builder()
                .size(source.getSize())
                .page(source.getNumber())
                .totalElements(source.getTotalElements())
                .totalPages(source.getTotalPages())
                .contents(this.map(source.getContent()))
                .build();
    }
    
    default PageDTO<D> map(PageModel<S> source) {
        if (source == null) {
            return PageDTO.empty();
        }
        
        var contents = this.map(source.getContents());
        
        return new PageDTO<>(source.getSize(), source.getPage(), source.getTotalElements(), source.getTotalPages(), contents);
    }
}
