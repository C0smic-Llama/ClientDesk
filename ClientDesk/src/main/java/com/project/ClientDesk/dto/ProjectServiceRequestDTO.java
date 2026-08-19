package com.project.ClientDesk.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectServiceRequestDTO {


    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Service ID is required")
    private Long serviceCatalogueId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity  must be atleast 1")
    private Integer quantity;

    @NotNull(message = "Agreed price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Agreed price cannot be negative")
    private BigDecimal agreedPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount cannot be negative")
    private BigDecimal discount;

    private String remarks;
}
