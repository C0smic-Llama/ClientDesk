package com.project.ClientDesk.entity;


import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Payment extends  Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "invoice_id",nullable = false)
    private Invoice invoice;

    @Column(nullable = false,unique = true,length = 30)
    private String receiptNumber;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(length = 100)
    private String transactionReference;

    @Column(length = 500)
    private String remarks;

    public enum PaymentMethod{
        CASH,
        UPI,
        BANK_TRANSFER,
        CREDIT_CARD,
        DEBIT_CARD,
        CHEQUE
    }
}
