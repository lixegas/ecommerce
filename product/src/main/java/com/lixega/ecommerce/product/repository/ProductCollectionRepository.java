package com.lixega.ecommerce.product.repository;

import com.lixega.ecommerce.product.model.entity.ProductCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCollectionRepository extends MongoRepository<ProductCollection, String> {
    Optional<ProductCollection> findByProductId(Long id);
}
