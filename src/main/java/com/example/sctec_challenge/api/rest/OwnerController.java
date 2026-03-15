package com.example.sctec_challenge.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sctec_challenge.application.dto.owner.CreateOwnerDTO;
import com.example.sctec_challenge.application.dto.owner.OwnerDTO;
import com.example.sctec_challenge.application.usecase.CreateOwnerUseCaseImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OwnerController {
    
    CreateOwnerUseCaseImpl createOwnerUseCase;
    
    @PostMapping
    public ResponseEntity<OwnerDTO> create(@RequestBody @Valid CreateOwnerDTO request) {
        OwnerDTO response = createOwnerUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

}
