package com.sales_control.pi.validation;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.sales_control.pi.dto.SaleFilterDTO;
import com.sales_control.pi.exception.SaleValidationException;
import java.time.LocalDate;

public class SaleValidations {

  public SaleFilterDTO parseAndValidate(String from, String to, String name, String category) {
    var f = (isNull(from) || from.isBlank()) ? null : parseDate(from);
    var t = (isNull(to) || to.isBlank()) ? null : parseDate(to);
    if (nonNull(f) && nonNull(t) && f.isAfter(t))
      throw new SaleValidationException("Data inicial após a final");
    return new SaleFilterDTO(from, to, name, category);
  }

  private LocalDate parseDate(String s) {
    try {
      return LocalDate.parse(s);
    } catch (Exception e) {
      throw new SaleValidationException("Data inválida");
    }
  }
}
