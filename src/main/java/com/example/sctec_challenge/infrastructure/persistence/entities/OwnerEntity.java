package com.example.sctec_challenge.infrastructure.persistence.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "owner")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OwnerEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    
    @Column(nullable = false)
    String name;
    
    @Column(nullable = false, unique = true)
    String email;
    
    @Column(length = 11)
    String phone;
    
    @Column(length = 11, nullable = false)
    String cpf;
    
    @Column(name = "birth_date", nullable = false)
    LocalDate birthDate;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
    List<CompanyEntity> companies;
}
