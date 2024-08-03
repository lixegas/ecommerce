package com.lixega.ecommerce.product.service;

import com.lixega.ecommerce.product.mapper.ProductMapper;
import com.lixega.ecommerce.product.model.entity.Product;
import com.lixega.ecommerce.product.model.entity.ProductCollection;
import com.lixega.ecommerce.product.model.request.ProductCreationRequest;
import com.lixega.ecommerce.product.model.response.ProductResponse;
import com.lixega.ecommerce.product.repository.ProductCollectionRepository;
import com.lixega.ecommerce.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCollectionRepository productCollectionRepository;
    private final ProductMapper productMapper;


    public ProductResponse createProduct(ProductCreationRequest request) {

        Product product = productMapper.mapToProduct(request);
        product = productRepository.save(product);

        ProductCollection productCollection = productMapper.mapToProductCollection(request,product);
        productCollectionRepository.save(productCollection);

        return getProductResponse(product, productCollection);
    }


    private ProductResponse getProductResponse(Product product, ProductCollection productCollection) {
        return productMapper.mapToResponse(product,productCollection);
    }


    public ProductResponse getProductById(Long id){

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){ throw new ResponseStatusException(HttpStatus.NOT_FOUND);}

        Product product = productOptional.get();


        Optional<ProductCollection> productCollectionOptional = productCollectionRepository.findByProductId(id);
        if(productCollectionOptional.isEmpty()){ throw new ResponseStatusException(HttpStatus.NOT_FOUND);}

        ProductCollection productCollection =  productCollectionOptional.get();

        return productMapper.mapToResponse(product,productCollection);
    }

    public List<ProductResponse> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);


        Page<Product> pageProduct = productRepository.findAll(pageable);

        return pageProduct.stream()
                .map(product -> {
                    Optional<ProductCollection> productCollectionOptional = productCollectionRepository.findByProductId(product.getId());
                    if (productCollectionOptional.isEmpty()) {
                        return null;
                    }
                    ProductCollection productCollection = productCollectionOptional.get();
                    return productMapper.mapToResponse(product, productCollection);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
