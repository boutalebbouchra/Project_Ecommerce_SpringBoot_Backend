package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Product;

import java.util.UUID;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductByUuid(String uuid);

    void validateProduct(Product product,  boolean isUpdate);
    void deleteProductByUuid(String uuid);
    Product updateProduct(String uuid, Product updatedProduct);
    List<Product> getAllProducts();
}
