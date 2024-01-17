package com.example.projet_ecommerce.controllers;

import com.example.projet_ecommerce.entities.Promotion;
import com.example.projet_ecommerce.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping("/promotion")
    public ResponseEntity<Object> addPromotion(@RequestBody Promotion promotion) {
        try {
            Promotion savedPromotion = promotionService.savePromotion(promotion);
            return new ResponseEntity<>(savedPromotion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/promotion/{uuid}")
    public ResponseEntity<Object> updatePromotion(@PathVariable String uuid, @RequestBody Promotion updatedPromotion) {
        try {
            Promotion modifiedPromotion = promotionService.updatePromotion(uuid, updatedPromotion);
            return new ResponseEntity<>(modifiedPromotion, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
