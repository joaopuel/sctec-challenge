package com.example.sctec_challenge.domain.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import com.example.sctec_challenge.application.dto.pageable.PageDTO;
import com.example.sctec_challenge.domain.model.PageModel;

/**
 * Interface genérica para mapeamento entre objetos de diferentes tipos.
 * @param <S> Tipo de origem
 * @param <D> Tipo de destino
 */
public interface GenericMapper<S, D> {
    
    /**
     * Mapeia um objeto de origem para um objeto de destino.
     * @param source Objeto de origem a ser mapeado
     * @return Objeto de destino resultante do mapeamento
     */
    D map(S source);
    
    /**
     * Mapeia uma coleção de objetos de origem para uma lista de objetos de destino.
     * @param source Coleção de objetos de origem a ser mapeada
     * @return Lista de objetos de destino resultante do mapeamento
     */
    default List<D> map(Collection<S> source) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }
        
        return source.stream()
                .map(this::map)
                .toList();
    }
    
    /**
     * Mapeia um objeto Page de origem para um objeto PageModel de destino.
     * @param source Objeto Page de origem a ser mapeado
     * @return Objeto PageModel de destino resultante do mapeamento
     */
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
    
    /**
     * Mapeia um objeto PageModel de origem para um objeto PageDTO de destino.
     * @param source Objeto PageModel de origem a ser mapeado
     * @return Objeto PageDTO de destino resultante do mapeamento
     */
    default PageDTO<D> map(PageModel<S> source) {
        if (source == null) {
            return PageDTO.empty();
        }
        
        var contents = this.map(source.getContents());
        
        return new PageDTO<>(source.getSize(), source.getPage(), source.getTotalElements(), source.getTotalPages(), contents);
    }
}
