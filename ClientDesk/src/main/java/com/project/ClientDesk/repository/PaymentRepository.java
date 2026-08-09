package com.project.ClientDesk.repository;

import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.MonthlyRevenueProjection;
import com.project.ClientDesk.entity.Payment;
import com.project.ClientDesk.entity.TopClients;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByReceiptNumber(String receiptNumber);
    boolean existsByReceiptNumber(String receiptNumber);
    List<Payment> findByInvoice(Invoice invoice);
    Page<Payment> findByInvoice(Invoice invoice, Pageable pageable);
    Page<Payment> findByPaymentMethod(Payment.PaymentMethod paymentMethod, Pageable pageable);
    Page<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate, Pageable pagebale);
    Optional<Payment> findByTransactionReference(String transactionReference);

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.invoice = :invoice")
    BigDecimal getTotalPaidByInvoice(
            @Param("invoice")
            Invoice invoice);
    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getRevenueBetweenDates(
            @Param("startDate")
            LocalDate startDate,
            @Param("endDate")
            LocalDate endDate);


    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentDate BETWEEN : startDate AND :endDate")
    long countPaymentsBetweenDates(
            @Param("startDate")
            LocalDate startDate,
            @Param("endDate")
            LocalDate endDate);

    @Query("SELECT p FROM Payment p WHERE p.invoice.project.client.id = :clientId")
    Page<Payment> findByClientId(
            @Param("clientID")
            Long clientId,
            Pageable pageable);

    @Query("SELECT COAOLESCE(SUM(p.amount),0) FROM Payment p")
    BigDecimal getTotalRevenue();


    @Query(value = "SELECT DATE_FORMAT(p.payment_date, '%Y-%m') AS period," +
            "COALESCE(SUM(p.amount),0) AS revenue" +
            "FROM payments p" +
            "GROUP BY DATE_FORMAT(p.payment_date,'%Y-%m')" +
            "ORDER BY period ", nativeQuery = true)
    List<MonthlyRevenueProjection> getMonthlyRevenue();

    @Query("SELECT p.invoice.project.client.id,p.invoice.project.client.companyName,SUM(p.amount) FROM Payment p GROUP BY p.invoice.project.client.id,p.invoice.project.client.companyName ORDER BY SUM(p.amount) DESC")
    List<TopClients> findTopClientsByRevenue();


}
