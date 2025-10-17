package com.sales_control.pi.dto;

import lombok.Builder;

@Builder
public record SaleProductDTO(
    Integer productId,
    String name,
    String category,
    Double unitPrice,
    String unitOfMeasure,
    Integer quantity) {}
