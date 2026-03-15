package com.example.sctec_challenge.infrastructure.persistence.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.example.sctec_challenge.infrastructure.persistence.enums.BusinessSegment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    OwnerEntity owner;
    
    @Column(nullable = false)
    String municipality;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BusinessSegment businessSegment;
    
    @Column(nullable = false, unique = true)
    String email;
    
    @Column(length = 11)
    String phone;
    
    @Column(name = "foundation_date", nullable = false)
    LocalDate foundationDate;
    
    @Column(name = "is_active", nullable = false)
    Boolean isActive;
}
