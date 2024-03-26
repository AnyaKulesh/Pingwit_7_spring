package com.example.springdemo.converter.product;

import com.example.springdemo.controller.product.CreateProductInputDto;
import com.example.springdemo.controller.product.ProductDto;
import com.example.springdemo.repository.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.id(), product.name(), product.description(), product.price());
    }

    public Product convertToProduct (CreateProductInputDto input){
        return new Product(null,input.getName(),input.getDescription(), input.getPrice());
    }
}
