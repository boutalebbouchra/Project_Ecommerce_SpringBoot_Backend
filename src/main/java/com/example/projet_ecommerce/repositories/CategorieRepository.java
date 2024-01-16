package com.example.projet_ecommerce.repositories;

import com.example.projet_ecommerce.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

    Optional<Categorie> findByUuidCategorie(String uuid);

    Optional<Categorie> findByNameCategorie(String name);
}
