package com.sales_control.pi.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "venda")
public class SaleEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Integer id; // no banco é INT

  @Column(name = "datavenda", nullable = false)
  private LocalDateTime saleDate;

  @Column(name = "valortotal", nullable = false)
  private Double totalValue; // no banco é double

  @OneToMany(mappedBy = "saleEntity", cascade = ALL, orphanRemoval = true)
  private List<SaleProductEntity> productsSold;
}
