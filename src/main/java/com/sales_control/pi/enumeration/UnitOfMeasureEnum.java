package com.sales_control.pi.enumeration;

import com.sales_control.pi.exception.ValidationException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UnitOfMeasureEnum {
  UNIT("Unidade"),
  GRAMS("Gramas"),
  KILOS("Kilos"),
  LITERS("Litros"),
  MILLILITRES("Mililitros"),
  PACKAGE("Pacote"),
  BOX("Caixa"),
  METERS("Metros"),
  SQUARE_METERS("Metros Quadrados"),
  OTHERS("Outros");

  private final String translation;

  public static UnitOfMeasureEnum fromTranslation(String translation) {
    return Arrays.stream(values())
        .filter(c -> c.translation.equalsIgnoreCase(translation))
        .findFirst()
        .orElseThrow(() -> new ValidationException("Unidade de medida inválida"));
  }
}
