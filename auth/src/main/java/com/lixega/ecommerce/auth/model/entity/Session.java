package com.lixega.ecommerce.auth.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @Column(name = "session_uuid")
    private String sessionUuid;

    @Column(name = "expiration_date", nullable = false)
    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "access_uuid", nullable = false)
    private Access access;
}
