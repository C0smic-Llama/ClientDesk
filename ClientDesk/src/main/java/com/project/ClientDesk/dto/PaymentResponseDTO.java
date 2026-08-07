package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long id;
    private String receiptNumber;
    private Long invoiceId;
    private String invoiceNumber;
    private Invoice.InvoiceStatus status;
    private String clientName;
    private String projectName;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private Payment.PaymentMethod paymentMethod;
    private String transactionReference;
    private BigDecimal totalPaid;
    private BigDecimal pendingAmount;
    private String remarks;

}
