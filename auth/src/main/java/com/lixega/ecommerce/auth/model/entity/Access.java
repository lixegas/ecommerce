package com.lixega.ecommerce.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "access")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Access {

    @Id
    @Column(name = "access_uuid")
    private String accessUuid;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "access_timestamp", nullable = false)
    private Instant accessTimestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_credentials", nullable = false)
    private UserCredentials userCredentials;

    @OneToOne
    @JoinColumn(name = "refresh_token_uuid")
    private RefreshToken refreshToken;

    @JsonIgnore
    @OneToMany(mappedBy = "access", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Session> session = new ArrayList<>();
}
