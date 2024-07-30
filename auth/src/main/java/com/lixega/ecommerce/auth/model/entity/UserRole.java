package com.lixega.ecommerce.auth.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_credentials_id")
    private UserCredentials userCredentials;

    @ManyToOne
    @JoinColumn(name = "role_name")
    private Role role;
}
