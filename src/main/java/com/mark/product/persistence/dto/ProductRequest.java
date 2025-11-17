package com.mark.product.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name must be present!")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters!")
    private String name;

    @NotNull(message = "Product price must be defined!")
    private Float price;

    private String productDescription;
}
