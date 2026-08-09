package com.project.ClientDesk.entity;

import java.math.BigDecimal;

public interface MostRequestedServices {
    long getId();
    String getServiceName();
    BigDecimal getCount();
}
