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





}
