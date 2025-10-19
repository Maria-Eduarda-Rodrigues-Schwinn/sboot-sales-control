package com.sales_control.pi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequestDTO(
    @NotBlank String name,
    @NotBlank String category,
    @NotBlank String unitOfMeasure,
    @NotNull @DecimalMin("0.01") Double unitPrice,
    @NotNull @Min(0) Integer quantity) {}
