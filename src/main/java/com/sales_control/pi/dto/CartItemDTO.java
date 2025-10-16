package com.sales_control.pi.dto;

import lombok.Builder;

@Builder
public record CartItemDTO(Integer productId, Integer quantity) {}
