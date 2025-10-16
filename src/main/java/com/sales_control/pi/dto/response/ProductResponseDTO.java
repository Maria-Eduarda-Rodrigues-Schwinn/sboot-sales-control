package com.sales_control.pi.dto.response;

import lombok.Builder;

@Builder
public record ProductResponseDTO(
    Integer id,
    String name,
    String category,
    Double unitPrice,
    String unitOfMeasure,
    Integer quantity) {}
