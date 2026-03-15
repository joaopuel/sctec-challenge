package com.example.sctec_challenge.infrastructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sctec_challenge.infrastructure.persistence.entities.OwnerEntity;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, UUID> {
    
    boolean existsByCpf(String cpf);
    
    OwnerEntity getReferenceByCpf(String cpf);
}
