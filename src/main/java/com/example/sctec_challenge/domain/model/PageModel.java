package com.example.sctec_challenge.domain.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.util.CollectionUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Mutable representation of a paginated slice returned by domain services.
 *
 * @param <T> type stored inside each page
 */
@Data
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageModel<T> {
    
    int size;
    int page;
    long totalElements;
    int totalPages;
    Collection<T> contents;
    
    public static <T> PageModel<T> empty() {
        return PageModel.<T>builder()
            .size(0)
            .page(0)
            .totalElements(0L)
            .totalPages(0)
            .contents(Collections.emptyList())
            .build();
    }
    
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(contents);
    }
}
