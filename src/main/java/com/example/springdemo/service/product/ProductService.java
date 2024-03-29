package com.example.springdemo.service.product;

import com.example.springdemo.controller.product.CreateProductInputDto;
import com.example.springdemo.controller.product.ProductDto;
import com.example.springdemo.controller.product.UpdateProductInputDto;
import com.example.springdemo.converter.product.ProductConverter;
import com.example.springdemo.exception.PingwitException;
import com.example.springdemo.repository.product.Product;
import com.example.springdemo.repository.product.ProductRepository;
import com.example.springdemo.validator.product.ProductValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductConverter productConverter, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.productValidator = productValidator;
    }

    public List<ProductDto> findAllProducts() {
        return productRepository.getAllProducts().stream()
                .map(productConverter::convertToProductDto)
                .toList();
    }

    public ProductDto getProductById(Integer id) {
        Optional<Product> productById = productRepository.findProductById(id);
        return productById.map(productConverter ::convertToProductDto)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Integer createProduct (CreateProductInputDto input ){
        productValidator.validateOnCreate(input);
        Product product = productConverter.convertToProduct(input);
        return productRepository.createProduct(product);
    }

    public void updateProduct(Integer id, UpdateProductInputDto inputDto) {
        Product existingProduct = productRepository.findProductById(id)
                .orElseThrow(() -> new PingwitException("User not found!"));

        Product productToUpdate = new Product(existingProduct.id(),
                existingProduct.name(),
                inputDto.getDescription(),
                inputDto.getPrice());
        productRepository.updateProduct(productToUpdate);
    }

    public void deleteProduct(Integer id){
        productRepository.deleteProductById(id);
    }
}
