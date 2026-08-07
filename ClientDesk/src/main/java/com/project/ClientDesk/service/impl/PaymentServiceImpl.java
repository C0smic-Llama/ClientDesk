package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.PaymentRequestDTO;
import com.project.ClientDesk.dto.PaymentResponseDTO;
import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.Payment;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.PaymentMapper;
import com.project.ClientDesk.repository.InvoiceRepository;
import com.project.ClientDesk.repository.PaymentRepository;
import com.project.ClientDesk.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentMapper paymentMapper;


    private String generateReceiptNumber(){
        long count = paymentRepository.count()+1;
        return String.format("RCPT-%d=-%05d", Year.now().getValue(),count);
    }

    private Invoice getInvoice(Long invoiceId){
        return invoiceRepository.findById(invoiceId).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found with ID : "+invoiceId));
    }

    private BigDecimal getTotalPaid(Invoice invoice){
        BigDecimal totalPaid = paymentRepository.getTotalPaidByInvoice(invoice);
        return totalPaid == null?BigDecimal.ZERO:totalPaid;
    }

    private BigDecimal getPendingAmount(Invoice invoice){
        return invoice.getGrandTotal().subtract(getTotalPaid(invoice));
    }

    private void updateInvoiceStatus(Invoice invoice){
        BigDecimal totalPaid = getTotalPaid(invoice);
        if(totalPaid.compareTo(invoice.getGrandTotal()) >= 0)
            invoice.setStatus(Invoice.InvoiceStatus.PAID);
        else if(totalPaid.compareTo(BigDecimal.ZERO)>0)
            invoice.setStatus(Invoice.InvoiceStatus.PARTIALLY_PAID);
        else
            invoice.setStatus(Invoice.InvoiceStatus.SENT);

        invoiceRepository.save(invoice);
    }

    private PaymentResponseDTO buildResponse(Payment payment){
        PaymentResponseDTO responseDTO = paymentMapper.toResponseDTO(payment);
        responseDTO.setTotalPaid(getTotalPaid(payment.getInvoice()));
        responseDTO.setPendingAmount(getPendingAmount(payment.getInvoice()));
        return responseDTO;
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO) {

        Invoice invoice = getInvoice(requestDTO.getInvoiceId());
        Payment payment = paymentMapper.toEntity(requestDTO);
        payment.setInvoice(invoice);
        payment.setReceiptNumber(generateReceiptNumber());
        Payment savedPayment = paymentRepository.save(payment);
        updateInvoiceStatus(invoice);

        BigDecimal totalPaid = getTotalPaid(invoice);
        BigDecimal pendingAmount = getPendingAmount(invoice);

        return buildResponse(savedPayment);

    }

    @Override
    public PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO requestDTO) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Payment not found with ID : "+id));
        Invoice invoice  = getInvoice(requestDTO.getInvoiceId());
        paymentMapper.updateEntityFromDTO(requestDTO, payment);
        payment.setInvoice(invoice);
        Payment updatedPayment = paymentRepository.save(payment);
        updateInvoiceStatus(invoice);

        return buildResponse(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Payment not found with ID : "+id));
        Invoice invoice = payment.getInvoice();
        paymentRepository.delete(payment);
        updateInvoiceStatus(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Payment not foudn with ID : "+id));
        return buildResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentByReceiptNumber(String receiptNumber) {
        Payment payment = paymentRepository.findByReceiptNumber(receiptNumber).orElseThrow(()->
                new ResourceNotFoundException("Payment not found with receiptNumber : "+receiptNumber));
        PaymentResponseDTO responseDTO = paymentMapper.toResponseDTO(payment);
        responseDTO.setTotalPaid(getTotalPaid(payment.getInvoice()));
        responseDTO.setPendingAmount(getPendingAmount(payment.getInvoice()));
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getPaymentsByInvoice(Long invoiceId, Pageable pageable) {
        Invoice invoice = getInvoice(invoiceId);
        return paymentRepository.findByInvoice(invoice,pageable)
                .map(this::buildResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getPaymentsByClients(Long clientId, Pageable pageable) {
        return paymentRepository.findByClientId(clientId, pageable)
                .map(this::buildResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getPaymentsByPaymentMethod(Payment.PaymentMethod method, Pageable pageable) {
        return paymentRepository.findByPaymentMethod(method, pageable)
                .map(this::buildResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate, pageable)
                .map(this::buildResponse);
    }
}
