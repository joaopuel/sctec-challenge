package com.example.sctec_challenge.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.example.sctec_challenge.infrastructure.persistence.enums.BusinessSegment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CompanyModel {
    
    UUID id;
    String name;
    OwnerModel owner;
    String municipality;
    String cnpj;
    BusinessSegment businessSegment;
    String email;
    String phone;
    LocalDate foundationDate;
    Boolean isActive;

}
