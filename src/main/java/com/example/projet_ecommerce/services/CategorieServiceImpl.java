package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Categorie;
import com.example.projet_ecommerce.repositories.CategorieRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Data
@Service
public class CategorieServiceImpl implements CategorieService {

    private static final Logger logger = LoggerFactory.getLogger(CategorieServiceImpl.class);
    private final CategorieRepository categorieRepo;

    @Autowired
    public CategorieServiceImpl(CategorieRepository categorieRepo) {
        this.categorieRepo = categorieRepo;
    }

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepo.findAll();
    }

    @Override
    public Categorie getCategorieByUuidCategorie(String uuid) {
        logger.info("Rechercher a une Categorie par uuid");
        try {
            Optional<Categorie> categorie = categorieRepo.findByUuidCategorie(uuid);
            return categorie.orElse(null);
        } catch (IllegalArgumentException e) {
            logger.error("UUID invalide : {}", uuid);
            throw new IllegalArgumentException("UUID invalide : {} " + uuid);
        }

    }

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        validateCategorie(categorie, false); //false pour tester sur l'existance de uuid
        try {
            Categorie savedCategorie = categorieRepo.save(categorie);
            logger.info("Categorie enregistré avec succès : {}", savedCategorie);
            return savedCategorie;
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de categorie : {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de l'enregistrement de Categorie.");
        }
    }

    @Override
    public void validateCategorie(Categorie categorie, boolean isUpdate) {
        validateMandatoryFields(categorie);
        if (!isUpdate) { //on test pour l'ajout de categorie le uuid doit être unique
            validateUniqueUuidCategory(categorie.getUuidCategorie());
            validateUniqueNameCategorie(categorie.getNameCategorie());
        }

    }

    private void validateMandatoryFields(Categorie categorie) {
        if (categorie.getNameCategorie() == null || categorie.getNameCategorie().trim().isEmpty()) {
            logger.error("Erreur lors de la validation des champs obligatoires");
            throw new IllegalArgumentException("Attention Certains champs Obligatoires sont vides");
        }
    }

    private void validateUniqueUuidCategory(String uuid) {
        Optional<Categorie> existingCategoryWithUuid = categorieRepo.findByUuidCategorie(uuid);
        if (existingCategoryWithUuid.isPresent()) {
            throw new IllegalArgumentException("Ce id de Catégorie existe déja");
        }
    }

    private void validateUniqueNameCategorie(String name) {
        Optional<Categorie> existingCategorieWithName = categorieRepo.findByNameCategorie(name);
        if (existingCategorieWithName.isPresent()) {
            throw new IllegalArgumentException("Ce nom de catégorie existe déja");
        }
    }


    @Override
    public void deleteCategorieByUuidCategorie(String uuid) {
        Categorie existingCategorie = getCategorieByUuidCategorie(uuid);
        if (existingCategorie == null) {
            throw new IllegalArgumentException("Categorie introuvable avec l'UUID : " + uuid);
        }
        try {
            categorieRepo.delete(existingCategorie);
            logger.info("La catéégorie est supprimé avec succès avec l'UUID : {}", uuid);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du Categorie: {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de la suppression du Categorie.");
        }
    }

    @Override
    public Categorie updateCategorie(String uuid, Categorie updatedCategorie) {
        Categorie existingCategorie = getCategorieByUuidCategorie(uuid);

        if (existingCategorie == null) {
            throw new IllegalArgumentException("Catégories introuvable avec uuid " + uuid);
        }
        validateCategorie(existingCategorie, true);
        existingCategorie.setNameCategorie(updatedCategorie.getNameCategorie());
        existingCategorie.setImageCategorie(updatedCategorie.getImageCategorie());

        try {
            Categorie savedCategorie = categorieRepo.save(existingCategorie);
            logger.info("La catégorie est Modifier avec succée : {}", savedCategorie);
            return savedCategorie;
        } catch (Exception e) {
            logger.error("erreur lors de mise a jour de categorie : {}", e.getMessage());
            throw new IllegalArgumentException("erreur lors de mise a jour de categorie");
        }
    }









}