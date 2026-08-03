package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.ServiceCatalogue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatalogueRequestDTO {


    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name cannot exceed 100 characters")
    private String serviceName;

    @NotBlank(message = "Service description is required")
    @Size(max = 1000, message = "Service description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Service category is required")
    private ServiceCatalogue.ServiceCategory category;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Base price cannot be negative")
    private BigDecimal basePrice;

}
