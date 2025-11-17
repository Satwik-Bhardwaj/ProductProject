package com.mark.product.persistence.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductListResponse {

    @NotNull
    private List<ProductResponse> allProducts;
}
