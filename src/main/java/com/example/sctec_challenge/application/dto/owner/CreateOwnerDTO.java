package com.example.sctec_challenge.application.dto.owner;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record CreateOwnerDTO (
        @NotNull(message = "Name must not be null")
        @NotEmpty(message = "Name must not be empty")
        String name,
        
        @NotNull(message = "Email must not be null")
        @Email(message = "Email should be valid format")
        String email,
        
        @Pattern(regexp = "\\d{11}", message = "Phone must be 11 digits (area code + number) and not contain any special characters or spaces")
        String phone,
        
        @NotNull(message = "CPF must not be null")
        @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits and not contain any special characters or spaces")
        String cpf,
        
        @NotNull(message = "Birth date must not be null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {

}
