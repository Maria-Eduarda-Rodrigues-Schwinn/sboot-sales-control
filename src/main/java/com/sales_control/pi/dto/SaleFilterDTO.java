package com.sales_control.pi.dto;

import lombok.Builder;

@Builder
public record SaleFilterDTO(String fromDate, String toDate, String name, String category) {}
