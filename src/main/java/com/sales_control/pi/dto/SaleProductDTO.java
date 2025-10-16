package com.sales_control.pi.dto;

import com.sales_control.pi.enumeration.CategoryEnum;
import com.sales_control.pi.enumeration.UnitOfMeasureEnum;
import lombok.Builder;

@Builder
public record SaleProductDTO(
    Integer productId,
    String name,
    CategoryEnum category,
    Double unitPrice,
    UnitOfMeasureEnum unitOfMeasure,
    Integer quantity) {}
