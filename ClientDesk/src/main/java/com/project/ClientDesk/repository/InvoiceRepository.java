package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.Project;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    boolean existsByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findByProject(Project project);
    Page<Invoice> getByStatus(Invoice.InvoiceStatus status, Pageable pageable);
    Page<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Invoice> findByDueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);


    @Query("SELECT i FROM Invoice i WHERE i.project.client.id = :clientID")
    Page<Invoice> findByClientId(
            @Param("clientId")
            Long clientId,
            Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE i.project.client.id = :clientID")
    List<Invoice> findByClientId(
            @Param("clientId")
            Long clientId);

    Page<Invoice> findByDueDateBeforeAndStatusNot(LocalDate date, Invoice.InvoiceStatus status, Pageable pageable);

    long countByStatus(Invoice.InvoiceStatus status);

    Page<Invoice> findByDueDateBefore(LocalDate date, Pageable pageable);

}
