package com.sales_control.pi.validation;

import com.sales_control.pi.exception.ProductValidationException;
import com.sales_control.pi.exception.ValidationException;
import java.math.BigDecimal;
import java.util.Map;

public class ProductValidations {

  public BigDecimal validateUnitPrice(String s) {
    try {
      var v = new BigDecimal(s);
      if (v.signum() < 0) throw new ValidationException("Preço não pode ser negativo");
      return v;
    } catch (Exception e) {
      throw new ValidationException("Preço inválido");
    }
  }

  public Integer validateQuantity(String s) {
    try {
      var v = Integer.parseInt(s);
      if (v < 0) throw new ValidationException("Quantidade não pode ser negativa");
      return v;
    } catch (Exception e) {
      throw new ValidationException("Quantidade inválida");
    }
  }

  public void parseAndValidate(Map<String, String> fields) {
    if (fields.getOrDefault("name", "").isBlank())
      throw new ProductValidationException("Nome é obrigatório");
  }
}
