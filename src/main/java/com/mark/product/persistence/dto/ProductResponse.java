package com.mark.product.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Float price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productDescription;
}
