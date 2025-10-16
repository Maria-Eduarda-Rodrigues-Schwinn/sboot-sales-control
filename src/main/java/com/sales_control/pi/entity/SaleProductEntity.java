package com.sales_control.pi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venda_produto")
public class SaleProductEntity {

  @EmbeddedId private SaleProductId id;

  @ManyToOne
  @MapsId("saleId")
  @JoinColumn(name = "venda_id")
  private SaleEntity saleEntity;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "produto_id")
  private ProductEntity product;

  @Column(name = "quantidade", nullable = false)
  private Integer quantity;

  @Column(name = "precounitario", nullable = false)
  private Double unitPrice;

  @Column(name = "valortotal", nullable = false)
  private Double totalValue;
}
