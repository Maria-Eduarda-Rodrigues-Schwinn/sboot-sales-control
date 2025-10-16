package com.sales_control.pi.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record SaleDTO(
    Integer id, LocalDateTime saleDate, Double totalValue, List<SaleProductDTO> productsSold) {}
