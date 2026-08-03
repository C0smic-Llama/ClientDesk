package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.ServiceCatalogue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.security.Provider;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatalogueResponseDTO {


    private Long id;
    private String serviceName;
    private String description;
    private ServiceCatalogue.ServiceCategory category;
    private BigDecimal basePrice;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
