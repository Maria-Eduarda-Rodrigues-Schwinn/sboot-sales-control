package com.sales_control.pi.dto;

import com.sales_control.pi.enumeration.UserTypeEnum;
import lombok.Builder;

@Builder
public record UserDTO(
    Integer id, String username, String name, String passwordHash, UserTypeEnum userType) {}
