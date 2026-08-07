package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.PaymentRequestDTO;
import com.project.ClientDesk.dto.PaymentResponseDTO;
import com.project.ClientDesk.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PaymentService {

    PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO);
    PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO requestDTO);
    void deletePayment(Long id);
    PaymentResponseDTO getPaymentById(Long id);
    PaymentResponseDTO getPaymentByReceiptNumber(String receiptNumber);
    Page<PaymentResponseDTO> getPaymentsByInvoice(Long invoiceId, Pageable pageable);
    Page<PaymentResponseDTO> getPaymentsByClients(Long clientId, Pageable pageable);
    Page<PaymentResponseDTO> getPaymentsByPaymentMethod(Payment.PaymentMethod method, Pageable pageable);
    Page<PaymentResponseDTO> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
