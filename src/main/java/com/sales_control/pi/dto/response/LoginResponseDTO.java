package com.sales_control.pi.dto.response;

import com.sales_control.pi.enumeration.UserTypeEnum;
import lombok.Builder;

@Builder
public record LoginResponseDTO(String token, UserTypeEnum role, String roleLabel) {}
