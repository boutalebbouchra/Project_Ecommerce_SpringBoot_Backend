package com.example.projet_ecommerce.controllers;
import com.example.projet_ecommerce.entities.Categorie;
import com.example.projet_ecommerce.services.CategorieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategorieController {
    private final Logger logger= LoggerFactory.getLogger(CategorieController.class);
    private final CategorieServiceImpl categorieServcie;

@Autowired
    public CategorieController(CategorieServiceImpl categorieServcie) {
        this.categorieServcie = categorieServcie;
    }

@PostMapping("/categorie")
public ResponseEntity<Object> addCatgorie(@RequestBody Categorie categorie){
    logger.info("Requête reçue pour ajouter une catégorie : {}", categorie);
    try {
        Categorie savedCategorie=categorieServcie.saveCategorie(categorie);
        if(savedCategorie!=null){
            logger.info("Catégorie est ajouté avec succès : {}", savedCategorie);
            return new ResponseEntity<>(savedCategorie, HttpStatus.CREATED);
        }
        else
        {
            logger.error("Echeque de l'ajout de catégorie ");
            return new ResponseEntity<>("Echec de l'ajout de catégorie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    catch (IllegalArgumentException e){
        logger.error("Erreur lors de la validation des champs : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
@PutMapping("/categorie/{uuid}")
public ResponseEntity<Object> updateCategorie(@PathVariable String uuid,@RequestBody Categorie categorie){
    logger.info("Requête reçue pour Modifier une catégorie : {}", categorie);
    try {
        Categorie updatedCategorie = categorieServcie.updateCategorie(uuid, categorie);
        if (updatedCategorie != null) {
            logger.info("La categorie est modifié avec succée");
            return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
        }
        else {
            logger.error("Échec de la mise à jour de la catégorie");
            return new ResponseEntity<>("Échec de la mise à jour de la catégorie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    catch (Exception e){
        logger.error("Erreur lors de la validation des champs de catégorie : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
@DeleteMapping("/categorie/{uuid}")
public ResponseEntity<Object> deleteCategorie(@PathVariable String uuid){
    try {
        categorieServcie.deleteCategorieByUuidCategorie(uuid) ;
        logger.info("Catégorie supprimé avec succée avec uuid: {}",uuid);
        return new ResponseEntity<>("Catégorie est supprimé avec succée", HttpStatus.OK);
    }
    catch (IllegalArgumentException e){
        logger.error("Erreur lors de la Suppression de categorie par uuid : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

@GetMapping("categorie/{uuid}")
public ResponseEntity<Object> getCategoriesParUuidCategorie(@PathVariable String uuid){
    try
    {
        Categorie findcategories=categorieServcie.getCategorieByUuidCategorie(uuid);
        if(findcategories!=null){
            return new ResponseEntity<>(findcategories,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("La Catégorie est inrouvable avec le uuid "+uuid,HttpStatus.NOT_FOUND);
        }
    }
    catch (IllegalArgumentException e){
        logger.error("Erreur lors de la recherche à catégorie par uuid : {}",e.getMessage());
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

}

@GetMapping("categories")
public ResponseEntity<List<Categorie>> getCategories(){
    List<Categorie> categories=categorieServcie.getAllCategories();
    return new ResponseEntity<>(categories,HttpStatus.OK);
}

}
