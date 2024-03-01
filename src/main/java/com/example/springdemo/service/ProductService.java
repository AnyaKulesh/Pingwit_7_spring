package com.example.springdemo.service;

import com.example.springdemo.controller.ProductDto;
import com.example.springdemo.repository.Product;
import com.example.springdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.getAllProducts().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ProductDto getProductById(Integer id) {
        Optional<Product> productById = productRepository.findProductById(id);
        return productById.map(this::mapToDto)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private ProductDto mapToDto(Product product) {
        return new ProductDto(product.id(), product.name(), product.description(), product.price());
    }
}
