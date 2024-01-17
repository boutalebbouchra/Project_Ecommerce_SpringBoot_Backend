package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Product;
import com.example.projet_ecommerce.entities.Promotion;
import com.example.projet_ecommerce.repositories.PromotionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        validatePromotion(promotion);

        try {
            Promotion savedPromotion = promotionRepository.save(promotion);
            logger.info("Promotion enregistrée avec succès : {}", savedPromotion);
            return savedPromotion;
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de la promotion : {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de l'enregistrement de la promotion.");
        }
    }

    @Override
    public void validatePromotion(Promotion promotion) {
        validateMandatoryFields(promotion);
    }

    private void validateMandatoryFields(Promotion promotion) {
        if (promotion.getStartDate() == null || promotion.getEndDate() == null) {
            logger.error("Erreur lors de la validation des champs obligatoires pour la promotion.");
            throw new IllegalArgumentException("Les champs obligatoires ne peuvent pas être vides.");
        }

        if (promotion.getEndDate().isBefore(promotion.getStartDate())) {
            logger.error("Erreur lors de la validation des dates pour la promotion.");
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début.");
        }
    }

    //API POUR MODIFIER UNE PROMOTION

    @Override
    public Promotion updatePromotion(String uuid, Promotion updatedPromotion) {
        Promotion existingPromotion = getPromotionByUuid(uuid);

        if (existingPromotion == null) {
            throw new IllegalArgumentException("Promotion introuvable avec l'UUID : " + uuid);
        }

        // Mettez à jour les champs nécessaires
        existingPromotion.setStartDate(updatedPromotion.getStartDate());
        existingPromotion.setEndDate(updatedPromotion.getEndDate());
        existingPromotion.setDescription(updatedPromotion.getDescription());

        // Validez la promotion mise à jour
        validatePromotion(existingPromotion);

        try {
            // Enregistrez la promotion mise à jour dans la base de données
            Promotion savedPromotion = promotionRepository.save(existingPromotion);

            logger.info("Promotion mise à jour avec succès : {}", savedPromotion);
            return savedPromotion;
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la promotion : {}", e.getMessage());
            throw new IllegalArgumentException("Erreur lors de la mise à jour de la promotion.");
        }
    }

    @Override
    public Promotion getPromotionByUuid(String uuid) {
        logger.info("Recherche de la promotion avec l'UUID : {}", uuid);
        try {
            Optional<Promotion> promotionOptional = promotionRepository.findByUuid(uuid);
            return promotionOptional.orElse(null);
        } catch (IllegalArgumentException e) {
            logger.error("UUID invalide : {}", uuid);
            throw new IllegalArgumentException("UUID invalide : " + uuid);
        }
    }





}
