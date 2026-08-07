package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.InvoiceRequestDTO;
import com.project.ClientDesk.dto.InvoiceResponseDTO;
import com.project.ClientDesk.entity.Invoice;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InvoiceService {

    InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO);

    InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO requestDTO);

    void deleteInvoice(Long id);

    InvoiceResponseDTO getInvoiceById(Long id);

    InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber);

    InvoiceResponseDTO getInvoiceByProject(Long projectId);

    Page<InvoiceResponseDTO> getInvoicesByClient(Long clientId, Pageable pageable);

    Page<InvoiceResponseDTO> getInvoicesByStatus(Invoice.InvoiceStatus status, Pageable pageable);

    Page<InvoiceResponseDTO> getInvoicesByInvoiceDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<InvoiceResponseDTO> getInvoicesByDueDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<InvoiceResponseDTO> getOverdueInvoices(Pageable pageable);
}
