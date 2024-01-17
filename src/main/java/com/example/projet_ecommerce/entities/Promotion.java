package com.example.projet_ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(36)")
    private String uuid;

    /*
    @Column(name = "new_price", nullable = false)
    private BigDecimal newPrice;
*/
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "promotions")
    private List<Product> products;

    public Promotion() {
        this.uuid = UUID.randomUUID().toString();
    }
}
