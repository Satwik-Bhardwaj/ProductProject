package com.mark.product.service;

import com.mark.product.exception.BadRequestException;
import com.mark.product.exception.DataNotFoundException;
import com.mark.product.exception.FailedToSaveDeleteException;
import com.mark.product.persistence.entities.Product;
import com.mark.product.persistence.dto.ProductListResponse;
import com.mark.product.persistence.dto.ProductRequest;
import com.mark.product.persistence.dto.ProductResponse;
import com.mark.product.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductListResponse getAllProducts() {
        List<Product> productList;
        try {
            productList = productRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all products: {}", e.getMessage());
            throw new DataNotFoundException("Failed to fetch products");
        }
        List<ProductResponse> productResponses = productList.stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            return response;
        }).toList();
        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setAllProducts(productResponses);
        return productListResponse;
    }

    @Override
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setProductDescription(product.getDescription());
        return response;
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {

        // Create new Product entity
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getProductDescription());
        // save to db
        Product savedProduct = saveProduct(product);

        // construct response
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setPrice(savedProduct.getPrice());
        response.setProductDescription(savedProduct.getDescription());
        return response;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        // fetch existing product
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to find product with id: {}", id);
            return new DataNotFoundException("Product not found");
        });
        // update fields
        existingProduct.setName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setDescription(productRequest.getProductDescription());
        // save to db
        Product updatedProduct = saveProduct(existingProduct);

        // construct response
        ProductResponse response = new ProductResponse();
        response.setId(updatedProduct.getId());
        response.setName(updatedProduct.getName());
        response.setPrice(updatedProduct.getPrice());
        response.setProductDescription(updatedProduct.getDescription());
        return response;
    }

    @Override
    public String deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (DataAccessException ex) {
            log.error("Database error while deleting product with id {}: {}", id, ex.getMessage());
            throw new FailedToSaveDeleteException("Database error while deleting product");
        } catch (Exception ex) {
            log.error("Unexpected error while deleting product with id {}: {}", id, ex.getMessage());
            throw new FailedToSaveDeleteException("Failed to delete product");
        }
        return "Product deleted successfully";
    }

    public Product saveProduct(Product product) {
        try {
            Product saved = productRepository.save(product);
            log.info("Product saved successfully: id={}", saved.getId());
            return saved;
        } catch (DataIntegrityViolationException ex) {
            // unique constraint, null constraint, FK constraint
            log.error("Data integrity violation while saving product: {}", ex.getMessage());
            throw new BadRequestException("Invalid product data");

        } catch (OptimisticLockingFailureException ex) {
            // version conflict
            log.error("Optimistic lock error for product: {}", ex.getMessage());
            throw new FailedToSaveDeleteException("Concurrent update detected");

        } catch (DataAccessResourceFailureException ex) {
            // DB connection failure
            log.error("Database resource failure while saving product: {}", ex.getMessage());
            throw new FailedToSaveDeleteException("Database connection error");

        } catch (DataAccessException ex) {
            // general Spring Data exception
            log.error("General database error: {}", ex.getMessage());
            throw new FailedToSaveDeleteException("Database error while saving product");

        } catch (Exception ex) {
            // fallback unexpected error
            log.error("Unexpected error while saving product", ex);
            throw new FailedToSaveDeleteException("Failed to save product");
        }
    }
}
