package com.project.ClientDesk.entity;

import java.math.BigDecimal;

public interface MostRequestedServices {
    Long getId();
    String getServiceName();
    BigDecimal getCount();
}
