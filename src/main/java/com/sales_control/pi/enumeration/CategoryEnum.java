package com.sales_control.pi.enumeration;

import com.sales_control.pi.exception.ValidationException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryEnum {
  FOOD("Alimentos"),
  BEVERAGES("Bebidas"),
  PERSONAL_HYGIENE("Higiene Pessoal"),
  CLEANING("Limpeza"),
  ELECTRONICS("Eletrônicos"),
  CLOTHING("Roupas"),
  FURNITURE("Móveis"),
  BOOKS("Livros"),
  TOYS("Brinquedos"),
  OTHERS("Outros");

  private final String translation;

  public static CategoryEnum fromTranslation(String translation) {
    return Arrays.stream(values())
        .filter(c -> c.translation.equalsIgnoreCase(translation))
        .findFirst()
        .orElseThrow(() -> new ValidationException("Categoria inválida"));
  }
}
