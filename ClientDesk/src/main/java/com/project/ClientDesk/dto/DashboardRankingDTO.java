package com.project.ClientDesk.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardRankingDTO {

    private Long id;
    private String name;
    private BigDecimal value;
}
