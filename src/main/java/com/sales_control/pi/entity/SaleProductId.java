package com.sales_control.pi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SaleProductId implements Serializable {

  @Column(name = "venda_id")
  private Integer vendaId;

  @Column(name = "produto_id")
  private Integer produtoId;

  @Override
  public boolean equals(Object o) {
    if (this.equals(o)) return true;
    if (!(o instanceof SaleProductId that)) return false;
    return vendaId.equals(that.vendaId) && produtoId.equals(that.produtoId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vendaId, produtoId);
  }
}
