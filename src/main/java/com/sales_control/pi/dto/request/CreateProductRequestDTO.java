package com.sales_control.pi.dto.request;

public record CreateProductRequestDTO(
    String name, String category, String unitOfMeasure, Double unitPrice, Integer quantity) {}
