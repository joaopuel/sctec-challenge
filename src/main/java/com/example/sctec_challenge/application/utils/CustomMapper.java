package com.example.sctec_challenge.application.utils;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CustomMapper extends ModelMapper {
    
    Set<Class<?>> allowedNullSources = new HashSet<>();
    
    @PostConstruct
    private void setConfigurations() {
        this.getConfiguration() //
                .setMatchingStrategy(MatchingStrategies.STRICT) //
                .setFieldMatchingEnabled(true) //
                .setAmbiguityIgnored(true) //
                .setSkipNullEnabled(true) //
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) //
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE);
    }
    
    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if (destinationType == null) {
            return null;
        }
        
        if (source == null && !allowedNullSources.contains(destinationType)) {
            source = new Object();
        }
        
        return super.map(source, destinationType);
    }
    
    public void allowNullSources(Class<?> destinationType) {
        allowedNullSources.add(destinationType);
    }
}
