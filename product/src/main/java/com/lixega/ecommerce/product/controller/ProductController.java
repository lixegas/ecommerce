package com.lixega.ecommerce.product.controller;

import com.lixega.ecommerce.product.model.request.ProductCreationRequest;
import com.lixega.ecommerce.product.model.response.ProductResponse;
import com.lixega.ecommerce.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('product.view')")
    @GetMapping({"{id}"})
    public ProductResponse getProduct(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PreAuthorize("hasRole('product.view')")
    @GetMapping
    public List<ProductResponse> allProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return productService.getAllProduct(page, size);
    }

    @PreAuthorize("hasRole('product.create')")
    @PostMapping
    public ProductResponse creationProduct(@RequestBody ProductCreationRequest request){
        return productService.createProduct(request);
    }
}
