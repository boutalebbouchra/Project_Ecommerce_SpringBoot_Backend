package com.example.projet_ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(36)")
    private String uuidCategorie;

    @Column(nullable = false)
    private String nameCategorie;

    @Column
    private String imageCategorie;

    public Categorie() {
        this.uuidCategorie= UUID.randomUUID().toString();
    }

}
