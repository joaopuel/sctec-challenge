package com.example.sctec_challenge.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
public class OwnerModel {
    
    UUID id;
    String name;
    String email;
    String phone;
    String cpf;
    LocalDate birthDate;
    List<CompanyModel> companies;
    
}
