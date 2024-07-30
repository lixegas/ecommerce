package com.lixega.ecommerce.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {
    @Id
    private String name;

    private String description;
    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<UserRole> userRoles = new ArrayList<>();
}
