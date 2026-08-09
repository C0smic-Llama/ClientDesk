package com.project.ClientDesk.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummaryDTO {

    private long totalClients;

    private long totalProjects;
    private long activeProjects;
    private long completedProjects;

    private BigDecimal totalRevenue;
    private BigDecimal outstandingAmount;
    private BigDecimal totalPaymentsReceived;

    private long paidInvoices;
    private long partiallyPaidInvoices;
    private long overdueInvoices;

    private long pendingDeliverables;
    private long completedDeliverables;
}
