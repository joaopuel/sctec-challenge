package com.example.sctec_challenge.domain.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

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
}
