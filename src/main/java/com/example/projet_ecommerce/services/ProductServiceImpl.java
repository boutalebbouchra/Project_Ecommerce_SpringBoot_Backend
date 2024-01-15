package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Product;
import com.example.projet_ecommerce.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        validateProduct(product);

        try {
            // Continuez avec l'enregistrement du produit dans la base de données
            Product savedProduct = productRepository.save(product);

            logger.info("Produit enregistré avec succès : {}", savedProduct);
            return savedProduct;
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement du produit : {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de l'enregistrement du produit.");
        }
    }

    @Override
    public Product getProductByUuid(String uuid) {
        logger.info("Recherche du produit avec l'UUID : {}", uuid);
        try {
            Optional<Product> productOptional = productRepository.findByUuid(uuid);
            return productOptional.orElse(null);
        } catch (IllegalArgumentException e) {
            logger.error("UUID invalide : {}", uuid);
            throw new IllegalArgumentException("UUID invalide : " + uuid);
        }
    }


    public void validateProduct(Product product) {
        validateMandatoryFields(product);
        validatePositivePrice(product.getPrice());
        validatePositiveStockQuantity(product.getStockQuantity());
        validateDescriptionLength(product.getDescription());
        validatePastDateAdded(product.getDateAdded());
        validateUniqueUUID(product.getUuid());
        validateUniqueName(product.getName());
    }

    private void validatePositivePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif.");
        }
    }

    private void validateDescriptionLength(String description) {
        if (!StringUtils.isEmpty(description) && description.length() > 100) {
            throw new IllegalArgumentException("La description ne peut pas dépasser 100 caractères.");
        }
    }

    private void validatePastDateAdded(LocalDate dateAdded) {
        if (dateAdded != null && dateAdded.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'ajout ne peut pas être une date future.");
        }
    }

    private void validatePositiveStockQuantity(Integer stockQuantity) {
        if (stockQuantity != null && stockQuantity < 0) {
            throw new IllegalArgumentException("La quantité en stock ne peut pas être négative.");
        }
    }

    private void validateMandatoryFields(Product product) {
        if (StringUtils.isEmpty(product.getName()) || product.getPrice() == null || product.getStockQuantity() == null || StringUtils.isEmpty(product.getCategory())) {
            logger.error("Erreur lors de la validation des champs obligatoires.");
            throw new IllegalArgumentException("Certains champs obligatoires sont vides.");
        }
    }

    private void validateUniqueUUID(String uuid) {
        Optional<Product> existingProductWithUUID = productRepository.findByUuid(uuid);
        if (existingProductWithUUID.isPresent()) {
            throw new IllegalArgumentException("Un produit avec cet UUID existe déjà.");
        }
    }




    private void validateUniqueName(String name) {
        Optional<Product> existingProductWithName = productRepository.findByName(name);
        if (existingProductWithName.isPresent()) {
            throw new IllegalArgumentException("Un produit avec ce nom existe déjà.");
        }
    }
}