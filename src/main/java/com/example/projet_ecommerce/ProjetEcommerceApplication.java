package com.example.projet_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.projet_ecommerce.entities")
public class ProjetEcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetEcommerceApplication.class, args);
	}
}
