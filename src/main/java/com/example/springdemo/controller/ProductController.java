package com.example.springdemo.controller;

import com.example.springdemo.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.findAllProducts();
    }
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable(name = "id") Integer id){
        return productService.getProductById(id);
    }
}
