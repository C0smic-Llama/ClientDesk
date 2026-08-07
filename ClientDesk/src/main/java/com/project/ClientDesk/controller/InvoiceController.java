package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.InvoiceRequestDTO;
import com.project.ClientDesk.dto.InvoiceResponseDTO;
import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.service.InvoicePdfService;
import com.project.ClientDesk.service.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoice Management", description = "APIs to manage the invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoicePdfService invoicePdfService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(
            @Valid
            @RequestBody
            InvoiceRequestDTO requestDTO) {
        InvoiceResponseDTO responseDTO = invoiceService.createInvoice(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            InvoiceRequestDTO requestDTO) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable
            Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByInvoiceNumber(
            @PathVariable
            String invoiceNumber) {
        return ResponseEntity.ok(invoiceService.getInvoiceByNumber(invoiceNumber));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByProject(
            @PathVariable
            Long projectId) {
        return ResponseEntity.ok(invoiceService.getInvoiceByProject(projectId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByClient(
            @PathVariable
            Long clientId,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getInvoicesByClient(clientId, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByStatus(
            @PathVariable
            Invoice.InvoiceStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status, pageable));
    }

    @GetMapping("/invoice-date")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByInvoiceDateRange(
            @RequestParam
            LocalDate startDate,
            @RequestParam
            LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getInvoicesByInvoiceDateRange(startDate, endDate, pageable));
    }

    @GetMapping("/due-date")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByDueDateRange(
            @RequestParam
            LocalDate startDate,
            @RequestParam
            LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getInvoicesByDueDateRange(startDate, endDate, pageable));
    }

    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable
            Long invoiceId){
        byte[] pdf = invoicePdfService.generateInvoicePdf(invoiceId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=Invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    @GetMapping("/overdue")
    public ResponseEntity<Page<InvoiceResponseDTO>> getOverdueInvoices(Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getOverdueInvoices(pageable));
    }


}
