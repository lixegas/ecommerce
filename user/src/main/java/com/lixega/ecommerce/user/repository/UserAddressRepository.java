package com.lixega.ecommerce.user.repository;

import com.lixega.ecommerce.user.model.entity.UserAddress;
import com.lixega.ecommerce.user.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUserProfile(UserProfile userProfile);

    Optional<UserAddress> findByUserProfileAndId(UserProfile userProfile, Long id);
}
