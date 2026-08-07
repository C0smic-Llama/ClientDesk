package com.project.ClientDesk.dto;


import com.project.ClientDesk.entity.Payment;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "Invoice ID is required")
    private Long invoiceId;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Payment method is required")
    private Payment.PaymentMethod paymentMethod;

    @Size(max = 100, message = "Transaction reference cannot exceed 100 characters")
    private String transactionReference;

    @Size(max = 500, message = "Remarks cannot exceed 500 characters")
    private String remarks;
}
