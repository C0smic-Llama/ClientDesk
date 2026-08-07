package com.project.ClientDesk.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequestDTO {


    @NotNull(message = "Project ID is required")
    private Long projectId;

    @FutureOrPresent(message = "Due date should be in the future")
    private LocalDate dueDate;

    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    private String notes;
}
