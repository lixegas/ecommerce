package com.lixega.ecommerce.user.repository;

import com.lixega.ecommerce.user.model.entity.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long>  {

    UserProfile findUserById(Long id);
}
