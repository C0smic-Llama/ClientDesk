package com.project.ClientDesk.service;

import com.project.ClientDesk.dto.DashboardRankingDTO;
import com.project.ClientDesk.dto.DashboardRevenueDTO;
import com.project.ClientDesk.dto.DashboardSummaryDTO;

import java.util.List;

public interface DashboardService {


    DashboardSummaryDTO getDashbaordSummary();
    List<DashboardRevenueDTO> getRevenue();
    List<DashboardRankingDTO> getTopCLients();
    List<DashboardRankingDTO> getMostRequestedServices();
}
