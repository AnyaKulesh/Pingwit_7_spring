package com.example.springdemo.repository.product;

import java.math.BigDecimal;

public record Product(Integer id, String name, String description, BigDecimal price) {
}
