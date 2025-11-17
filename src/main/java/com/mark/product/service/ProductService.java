package com.mark.product.service;

import com.mark.product.persistence.dto.ProductListResponse;
import com.mark.product.persistence.dto.ProductRequest;
import com.mark.product.persistence.dto.ProductResponse;
import jakarta.validation.Valid;

public interface ProductService {

    ProductListResponse getAllProducts();

    ProductResponse findProduct(Long id);

    ProductResponse addProduct(@Valid ProductRequest productRequest);

    ProductResponse updateProduct(Long id, @Valid ProductRequest productRequest);

    String deleteProduct(Long id);
}
