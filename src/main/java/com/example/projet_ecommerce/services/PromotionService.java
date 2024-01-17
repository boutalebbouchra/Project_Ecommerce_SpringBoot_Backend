package com.example.projet_ecommerce.services;

import com.example.projet_ecommerce.entities.Promotion;


public interface PromotionService {
    Promotion savePromotion(Promotion promotion);
    void validatePromotion(Promotion promotion);
}
