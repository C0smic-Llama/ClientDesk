package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.DashboardRankingDTO;
import com.project.ClientDesk.dto.DashboardRevenueDTO;
import com.project.ClientDesk.dto.DashboardSummaryDTO;

import java.util.List;

public interface DashboardService {


    DashboardSummaryDTO getDashboardSummary();
    List<DashboardRevenueDTO> getRevenue();
    List<DashboardRankingDTO> getTopClients();
    List<DashboardRankingDTO> getMostRequestedServices();
}
