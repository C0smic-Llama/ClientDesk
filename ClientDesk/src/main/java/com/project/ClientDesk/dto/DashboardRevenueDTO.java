package com.project.ClientDesk.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardRevenueDTO {
    private String period;
    private BigDecimal revenue;
}
