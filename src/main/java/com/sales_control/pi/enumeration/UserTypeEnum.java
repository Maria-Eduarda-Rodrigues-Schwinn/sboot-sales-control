package com.sales_control.pi.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserTypeEnum {
  ADMIN("Administrador"),
  EMPLOYEE("Funcionário");

  private final String translation;
}
