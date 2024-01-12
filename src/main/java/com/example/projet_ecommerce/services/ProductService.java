package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Product;

import java.util.UUID;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductByUuid(String uuid);

    void validateProduct(Product product);
}
