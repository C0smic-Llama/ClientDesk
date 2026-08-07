package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Invoice;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponseDTO {


    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Invoice.InvoiceStatus status;

    private String projectName;

    private String clientName;

    private BigDecimal taxableAmount;
    private BigDecimal gstAmount;
    private BigDecimal discount;
    private BigDecimal grandTotal;

}
