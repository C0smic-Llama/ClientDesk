package com.project.ClientDesk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "project_services",
        uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {"project_id", "service_catalogue_id"}
            )
        })
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectService extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_service_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_catalogue_id", nullable = false)
    private ServiceCatalogue serviceCatalogue;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal agreedPrice;

    @Column(precision = 10, scale =2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(length = 500)
    private String remarks;

    public BigDecimal getLineTotal(){
        return agreedPrice.multiply(BigDecimal.valueOf(quantity)).subtract(discount);
    }

}
