package com.example.sctec_challenge.application.dto.owner;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Armazena dados necessários para criar um novo responsável pela empresa")
public record CreateOwnerDTO (
        
        @Schema(description = "Nome do responsável", example = "João Silva")
        @NotNull(message = "Name must not be null")
        @NotEmpty(message = "Name must not be empty")
        String name,
        
        @Schema(description = "Email do responsável", example = "joao.silva@email.com")
        @NotNull(message = "Email must not be null")
        @Email(message = "Email should be valid format")
        String email,
        
        @Schema(description = "Telefone do responsável (11 dígitos, incluindo DDD)", example = "11987654321")
        @Pattern(regexp = "\\d{11}", message = "Phone must be 11 digits (area code + number) and not contain any special characters or spaces")
        String phone,
        
        @Schema(description = "CPF do responsável (11 dígitos)", example = "12345678901")
        @NotNull(message = "CPF must not be null")
        @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits and not contain any special characters or spaces")
        String cpf,
        
        @Schema(description = "Data de nascimento do responsável", example = "1990-01-01")
        @NotNull(message = "Birth date must not be null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate
) {

}
