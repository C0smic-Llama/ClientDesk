package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.PaymentRequestDTO;
import com.project.ClientDesk.dto.PaymentResponseDTO;
import com.project.ClientDesk.entity.Payment;
import com.project.ClientDesk.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs to manage the payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @Valid
            @RequestBody
            PaymentRequestDTO requestDTO){
        PaymentResponseDTO responseDTO = paymentService.createPayment(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPayments(
            Pageable pageable) {
        return ResponseEntity.ok(
                paymentService.getAllPayments(pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            PaymentRequestDTO requestDTO){
        return ResponseEntity.ok(paymentService.updatePayment(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable
            Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable
            Long id){
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/receipt-number/{receiptNumber}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByReceiptNumber(
            @PathVariable
            String receiptNumber){
        return  ResponseEntity.ok(paymentService.getPaymentByReceiptNumber(receiptNumber));
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByInvoice(
            @PathVariable
            Long invoiceId,
            Pageable pageable){
        return ResponseEntity.ok(paymentService.getPaymentsByInvoice(invoiceId, pageable));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByClient(
            @PathVariable
            Long clientId,
            Pageable pageable){
        return ResponseEntity.ok(paymentService.getPaymentsByClients(clientId, pageable));
    }

    @GetMapping("/payment-method/{paymentMethod}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByPaymentMethod(
            @PathVariable
            Payment.PaymentMethod method,
            Pageable pageable){
        return  ResponseEntity.ok(paymentService.getPaymentsByPaymentMethod(method, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByDateRange(
            @RequestParam
            LocalDate startDate,
            @RequestParam
            LocalDate endDate,
            Pageable pageable){
        return  ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate, pageable));
    }

}
