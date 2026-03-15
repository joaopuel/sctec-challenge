package com.example.sctec_challenge.application.dto.company;

import java.time.LocalDate;

import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.infrastructure.persistence.enums.BusinessSegment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Armazena dados necessários para criar uma nova empresa")
public record CreateCompanyDTO(
        
        @Schema(description = "Nome da empresa", example = "Tech Solutions Ltda.")
        @NotNull(message = "Name must not be null")
        @NotEmpty(message = "Name must not be empty")
        String name,
        
        @Schema(description = "Responsável pela empresa")
        @NotNull(message = "Owner must not be null")
        OwnerDTO owner,
        
        @Schema(description = "Município em que a empresa está localizada", example = "São Paulo")
        @NotNull(message = "Municipality must not be null")
        @NotEmpty(message = "Municipality must not be empty")
        String municipality,
        
        @Schema(description = "CNPJ da empresa (14 dígitos)", example = "12345678000199")
        @NotNull(message = "Municipality must not be null")
        @Pattern(regexp = "\\d{14}", message = "CPF must be 14 digits and not contain any special characters or spaces")
        String cnpj,
        
        @Schema(description = "Segmento de atuação da empresa", example = "TECHNOLOGY")
        @NotNull(message = "BusinessSegment must not be null")
        BusinessSegment businessSegment,
        
        @Schema(description = "Email de contato da empresa", example = "suporte@tech.com")
        @NotNull(message = "Email must not be null")
        @Email(message = "Email should be valid format")
        String email,
        
        @Schema(description = "Telefone de contato da empresa (11 dígitos, incluindo DDD)", example = "11987654321")
        @Pattern(regexp = "\\d{11}", message = "Phone must be 11 digits (area code + number) and not contain any special characters or spaces")
        String phone,
        
        @Schema(description = "Data de fundação da empresa", example = "2000-01-01")
        @NotNull(message = "FoundationDate must not be null")
        @PastOrPresent(message = "FoundationDate must not be in the future")
        LocalDate foundationDate,
        
        @Schema(description = "Indica o status da empresa (ativa ou inativa)", example = "true")
        Boolean isActive
) {

}
