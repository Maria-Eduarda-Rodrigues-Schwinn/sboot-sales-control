package com.sales_control.pi.dto;

import com.sales_control.pi.enumeration.UserTypeEnum;
import lombok.Builder;

@Builder
public record LoginResponseDTO(String token, String role) {}
