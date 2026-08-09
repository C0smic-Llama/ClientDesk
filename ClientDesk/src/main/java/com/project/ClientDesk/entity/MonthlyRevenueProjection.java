package com.project.ClientDesk.entity;

import java.math.BigDecimal;

public interface MonthlyRevenueProjection {
    String getPeriod();
    BigDecimal getRevenue();
}
