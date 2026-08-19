package com.project.ClientDesk.controller;


import com.project.ClientDesk.dto.DashboardRankingDTO;
import com.project.ClientDesk.dto.DashboardRevenueDTO;
import com.project.ClientDesk.dto.DashboardSummaryDTO;
import com.project.ClientDesk.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "APIs to get dashboard info")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getMainDashboardDetails(){
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<DashboardRevenueDTO>> getDashboardRevenue(){
        return ResponseEntity.ok(dashboardService.getRevenue());
    }

    @GetMapping("/top-clients")
    public ResponseEntity<List<DashboardRankingDTO>> getTopClients(){
        return ResponseEntity.ok(dashboardService.getTopClients());
    }

    @GetMapping("/services")
    public ResponseEntity<List<DashboardRankingDTO>> getMostRequestedServices(){
        return ResponseEntity.ok(dashboardService.getMostRequestedServices());
    }
}
