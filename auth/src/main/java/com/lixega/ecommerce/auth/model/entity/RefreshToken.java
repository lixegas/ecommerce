package com.lixega.ecommerce.auth.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RefreshToken {

    @Id
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private Instant expiryDate;

    private boolean valid;

    @OneToOne
    @JoinColumn(name = "access_uuid", nullable = false)
    private Access access;
}
