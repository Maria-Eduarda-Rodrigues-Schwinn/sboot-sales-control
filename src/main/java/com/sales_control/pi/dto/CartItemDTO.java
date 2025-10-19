package com.sales_control.pi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CartItemDTO(
    @NotNull @Positive Integer productId, @NotNull @Positive Integer quantity) {}
