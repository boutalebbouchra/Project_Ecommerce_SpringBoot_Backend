package com.example.projet_ecommerce.controllers;

import com.example.projet_ecommerce.entities.Product;
import com.example.projet_ecommerce.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PutMapping("/product/{uuid}")
    public ResponseEntity<Object> updateProduct(@PathVariable String uuid, @RequestBody Product updatedProduct) {
        logger.info("Requête reçue pour mettre à jour un produit avec UUID : {}", uuid);

        try {
            Product updatedProductResult = productService.updateProduct(uuid, updatedProduct);

            if (updatedProductResult != null) {
                logger.info("Produit mis à jour avec succès : {}", updatedProductResult);
                return new ResponseEntity<>(updatedProductResult, HttpStatus.OK);
            } else {
                logger.error("Échec de la mise à jour du produit.");
                return new ResponseEntity<>("Échec de la mise à jour du produit.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la validation des champs : {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{uuid}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String uuid) {
        try {
            productService.deleteProductByUuid(uuid);
            logger.info("Produit supprimé avec succès avec l'UUID : {}", uuid);
            return new ResponseEntity<>("Produit supprimé avec succès.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Erreur lors de la suppression du produit : {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{uuid}")
    public ResponseEntity<Object> getProductByUuid(@PathVariable String uuid) {
        try {
            Product product = productService.getProductByUuid(uuid);

            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Produit introuvable avec l'UUID : " + uuid, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}