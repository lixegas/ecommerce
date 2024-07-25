package com.lixega.ecommerce.auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_history")
@Data
public class AccessHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "access_timestamp", nullable = false)
    private LocalDateTime accessTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_credentials", nullable = false)
    private UserCredentials userCredentials;
}
