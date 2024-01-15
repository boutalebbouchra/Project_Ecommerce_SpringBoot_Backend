package com.example.projet_ecommerce.controllers;

import com.example.projet_ecommerce.entities.Product;
import com.example.projet_ecommerce.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        logger.info("Requête reçue pour ajouter un produit : {}", product);

        try {
            Product savedProduct = productService.saveProduct(product);

            if (savedProduct != null) {
                logger.info("Produit ajouté avec succès : {}", savedProduct);
                return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            } else {
                logger.error("Échec de l'ajout du produit.");
                return new ResponseEntity<>("Échec de l'ajout du produit.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la validation des champs : {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}