package com.example.sctec_challenge.application.dto.pageable;

import jakarta.validation.constraints.Min;

public record PaginationDTO(

        @Min(value = 0, message = "Size must be greater than or equal to 0") int page,

        @Min(value = 1, message = "Page must be greater than or equal to 1") int size) {

}

