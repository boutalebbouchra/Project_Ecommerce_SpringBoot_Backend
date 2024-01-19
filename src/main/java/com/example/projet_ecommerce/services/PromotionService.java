package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Promotion;

import java.util.List;


public interface PromotionService {
    Promotion savePromotion(Promotion promotion);
    void validatePromotion(Promotion promotion);

    Promotion updatePromotion(String uuid, Promotion updatedPromotion);
    Promotion getPromotionByUuid(String uuid);
    void deletePromotion(String uuid);

    List<Promotion> getAllPromotions();


}
