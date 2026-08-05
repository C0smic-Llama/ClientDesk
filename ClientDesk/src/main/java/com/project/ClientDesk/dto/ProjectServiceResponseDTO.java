package com.project.ClientDesk.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectServiceResponseDTO {
        private Long id;

        private Long projectId;
        private String projectName;

        private Long serviceCatalogueId;
        private String serviceName;

        private Integer quantity;
        private BigDecimal agreedPrice;
        private BigDecimal discount;
        private BigDecimal total;
        private BigDecimal lineTotal;

        private String remarks;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}
