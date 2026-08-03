package com.project.ClientDesk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "service_catalogue")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCatalogue extends Base {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name", nullable = false, unique = true,length = 100)
    private String serviceName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ServiceCategory category;

    @Column(name = "base_price", nullable = false,precision = 10,scale = 2)
    private BigDecimal basePrice;

    @Column(name= "active", nullable = false)
    private boolean active;

    public enum ServiceCategory{
        PRODUCTION,
        EDITING,
        DESIGN,
        BRANDING,
        DIGITAL_MARKETING,
        WEB_DEVELOPMENT,
        PHOTOGRAPHY,
        OTHER
    }
}
