package com.lixega.ecommerce.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private String userId;

    @OneToMany(mappedBy = "userProfile")
    private List<UserAddress> addresses = new ArrayList<>();

    private boolean enabled;
}
