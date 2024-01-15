package com.example.projet_ecommerce.entities;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "products")
public class Product {

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(36)")
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String category;

    @Column
    private String image;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    public Product() {
        this.uuid = UUID.randomUUID().toString();
        this.dateAdded = LocalDate.now();
    }
}
