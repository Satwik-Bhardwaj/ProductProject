package com.mark.product.controller;

import com.mark.product.persistence.dto.ProductListResponse;
import com.mark.product.persistence.dto.ProductRequest;
import com.mark.product.persistence.dto.ProductResponse;
import com.mark.product.persistence.dto.ResponseModel;
import com.mark.product.service.ProductService;
import com.mark.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Product API",
        description = "Operations related to managing products"
)
@Slf4j
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products", description = "Fetches a list of all products available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all products")
    })
    @GetMapping("")
    public ResponseEntity<ResponseModel<ProductListResponse>> getProductDetails() {
        log.info("Received request: GET /products");
        ProductListResponse response = productService.getAllProducts();
        log.info("Returning {} products", response.getAllProducts().size());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseUtil.success(response, "Successfully fetched all products")
        );
    }

    @Operation(summary = "Get product by ID", description = "Fetches the details of a specific product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the product")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<ProductResponse>> getProduct(@PathVariable() Long id) {
        log.info("Received request: GET /products/{}", id);
        ProductResponse response = productService.findProduct(id);
        log.info("Returning product details with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseUtil.success(response, "Successfully fetched the product")
        );
    }

    @Operation(summary = "Add a new product", description = "Adds a new product to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the product")
    })
    @PostMapping("")
    public ResponseEntity<ResponseModel<ProductResponse>> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        log.info("Received request: POST /products with body: {}", productRequest);
        ProductResponse response = productService.addProduct(productRequest);
        log.info("Added new product with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseUtil.success(response, "Successfully added the product")
        );
    }

    @Operation(summary = "Update an existing product", description = "Updates the details of an existing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<ProductResponse>> updateProduct(@PathVariable Long id,
                                                                        @RequestBody @Valid ProductRequest productRequest) {
        log.info("Received request: PUT /products/{} with body: {}", id, productRequest);
        ProductResponse response = productService.updateProduct(id, productRequest);
        log.info("Updated product with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseUtil.success(response, "Successfully updated the product")
        );
    }

    @Operation(summary = "Delete a product", description = "Deletes a specific product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the product")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<String>> deleteProduct(@PathVariable Long id) {
        log.info("Received request: DELETE /products/{}", id);
        String response = productService.deleteProduct(id);
        log.info("Deleted product with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseUtil.success(response, "Successfully deleted the product")
        );
    }
}
