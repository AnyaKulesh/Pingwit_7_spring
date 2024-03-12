package com.example.springdemo.controller;

import com.example.springdemo.service.ProductService;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Integer createProduct(@RequestBody CreateProductInputDto inputDto){
        return  productService.createProduct(inputDto);
    }
    @PutMapping
    public void updateProduct(@RequestBody UpdateProductInputDto inputDto, @PathVariable(name = "id") Integer id){
        productService.updateProduct(id,inputDto);
    }
    @DeleteMapping ("/{id}")
    public void deleteProduct(@PathVariable(name = "id") Integer id){
        productService.deleteProduct(id);
    }
}
