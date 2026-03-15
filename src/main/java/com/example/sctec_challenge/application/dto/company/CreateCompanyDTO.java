package com.example.sctec_challenge.application.dto.company;

import java.time.LocalDate;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.infrastructure.persistence.enums.BusinessSegment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

public record CreateCompanyDTO(
        
        @NotNull(message = "Name must not be null")
        @NotEmpty(message = "Name must not be empty")
        String name,
        
        @NotNull(message = "Owner must not be null")
        OwnerDTO owner,
        
        @NotNull(message = "Municipality must not be null")
        @NotEmpty(message = "Municipality must not be empty")
        String municipality,
        
        @NotNull(message = "Municipality must not be null")
        @Pattern(regexp = "\\d{14}", message = "CPF must be 14 digits and not contain any special characters or spaces")
        String cnpj,
        
        BusinessSegment businessSegment,
        
        @NotNull(message = "Email must not be null")
        @Email(message = "Email should be valid format")
        String email,
        
        @Pattern(regexp = "\\d{11}", message = "Phone must be 11 digits (area code + number) and not contain any special characters or spaces")
        String phone,
        
        @NotNull(message = "FoundationDate must not be null")
        @PastOrPresent(message = "FoundationDate must not be in the future")
        LocalDate foundationDate,
        
        Boolean isActive
) {

}
