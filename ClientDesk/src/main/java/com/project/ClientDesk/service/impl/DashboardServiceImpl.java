package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.DashboardRankingDTO;
import com.project.ClientDesk.dto.DashboardRevenueDTO;
import com.project.ClientDesk.dto.DashboardSummaryDTO;
import com.project.ClientDesk.entity.*;
import com.project.ClientDesk.repository.*;
import com.project.ClientDesk.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final DeliverableRepository deliverableRepository;
    private final ProjectServiceRepository projectServiceRepository;


    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        log.debug("Loading dashboard info");
        long totalClients = clientRepository.count();

        long totalProjects = projectRepository.count();
        long activeProjects = projectRepository.countByStatus(Project.ProjectStatus.IN_PROGRESS);
        long completedProjects = projectRepository.countByStatus(Project.ProjectStatus.COMPLETED);

        BigDecimal totalRevenue = paymentRepository.getTotalRevenue();
        BigDecimal outstandingAmount = invoiceRepository.getTotalOutstandingAmount(Invoice.InvoiceStatus.PAID);

        if(outstandingAmount==null)
            outstandingAmount = BigDecimal.ZERO;
        long paidInvoices = invoiceRepository.countByStatus(Invoice.InvoiceStatus.PAID);
        long partiallyPaidInvoices = invoiceRepository.countByStatus(Invoice.InvoiceStatus.PARTIALLY_PAID);
        long overdueInvoices = invoiceRepository.countByStatus(Invoice.InvoiceStatus.OVERDUE);

        long pendingDeliverables = deliverableRepository.countByStatus(Deliverable.DeliverableStatus.PENDING);
        long completedDeliverables = deliverableRepository.countByStatus(Deliverable.DeliverableStatus.COMPLETED);

        return DashboardSummaryDTO.builder()
                .totalClients(totalClients)
                .totalProjects(totalProjects)
                .activeProjects(activeProjects)
                .completedProjects(completedProjects)
                .totalRevenue(totalRevenue)
                .outstandingAmount(outstandingAmount)
                .paidInvoices(paidInvoices)
                .partiallyPaidInvoices(partiallyPaidInvoices)
                .overdueInvoices(overdueInvoices)
                .build();
    }

    @Override
    public List<DashboardRevenueDTO> getRevenue() {
        List<MonthlyRevenueProjection> results = paymentRepository.getMonthlyRevenue();

        return results.stream()
                .map(result->DashboardRevenueDTO.builder()
                        .period(result.getPeriod())
                        .revenue(result.getRevenue())
                        .build()).toList();
    }

    @Override
    public List<DashboardRankingDTO> getTopClients() {
        List<TopClients> results = paymentRepository.findTopClientsByRevenue();

        return results.stream()
                .map(result->DashboardRankingDTO.builder()
                        .id(result.getId())
                        .name(result.getCompanyName())
                        .value(result.getValue())
                        .build()).toList();
    }

    @Override
    public List<DashboardRankingDTO> getMostRequestedServices() {
        List<MostRequestedServices> results = projectServiceRepository.findMostRequestedServices();

        return results.stream()
                .map(result-> DashboardRankingDTO.builder()
                        .id(result.getId())
                        .name(result.getServiceName())
                        .value(BigDecimal.valueOf(((Number)result.getCount()).longValue()))
                        .build()).toList();
    }
}
