package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Categorie;

import java.util.List;

public interface CategorieService {

    Categorie saveCategorie(Categorie categorie);
    Categorie getCategorieByUuidCategorie(String uuid);

    void validateCategorie(Categorie categorie,  boolean isUpdate);
    void deleteCategorieByUuidCategorie(String uuid);
   Categorie updateCategorie(String uuid, Categorie updatedCategorie);
    List<Categorie> getAllCategories();

}
