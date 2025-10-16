package com.sales_control.pi.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.sales_control.pi.enumeration.CategoryEnum;
import com.sales_control.pi.enumeration.UnitOfMeasureEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", nullable = false)
  private String name;

  @Enumerated(STRING)
  @Column(name = "categoria", nullable = false)
  private CategoryEnum category;

  @Column(name = "precounitario", nullable = false)
  private Double unitPrice;

  @Enumerated(STRING)
  @Column(name = "unidademedida", nullable = false)
  private UnitOfMeasureEnum unitOfMeasure;

  @Column(name = "quantidade", nullable = false)
  private Integer quantity;
}
