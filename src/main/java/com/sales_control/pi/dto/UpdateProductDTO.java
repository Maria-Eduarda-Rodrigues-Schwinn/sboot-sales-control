package com.sales_control.pi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public record UpdateProductDTO(@DecimalMin("0.01") Double unitPrice, @Min(0) Integer quantity) {}
