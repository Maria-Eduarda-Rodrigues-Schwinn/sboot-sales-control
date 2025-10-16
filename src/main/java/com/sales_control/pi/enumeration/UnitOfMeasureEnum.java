package com.sales_control.pi.enumeration;

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

  UnitOfMeasureEnum(String translation) {
    this.translation = translation;
  }

  public String getTranslation() {
    return translation;
  }
}
