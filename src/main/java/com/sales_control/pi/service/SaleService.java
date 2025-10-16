package com.sales_control.pi.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.sales_control.pi.dto.CartDTO;
import com.sales_control.pi.dto.SaleDTO;
import com.sales_control.pi.dto.SaleFilterDTO;
import com.sales_control.pi.dto.SaleProductDTO;
import com.sales_control.pi.entity.SaleEntity;
import com.sales_control.pi.entity.SaleProductEntity;
import com.sales_control.pi.entity.SaleProductId;
import com.sales_control.pi.exception.SaleValidationException;
import com.sales_control.pi.repository.ProductRepository;
import com.sales_control.pi.repository.SaleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {

  private final SaleRepository saleRepo;
  private final ProductRepository productRepo;

  public Double calculateTotalSalesValue(List<SaleProductDTO> items) {
    if (isNull(items) || items.isEmpty()) throw new SaleValidationException("Lista vazia");

    return items.stream().map(i -> i.unitPrice() * i.quantity()).reduce(0.0, Double::sum);
  }

  @Transactional
  public SaleDTO finalizeSale(CartDTO cart) {
    if (isNull(cart.items()) || cart.items().isEmpty())
      throw new SaleValidationException("Carrinho vazio");

    var sale =
        SaleEntity.builder()
            .saleDate(LocalDateTime.now())
            .totalValue(0.0)
            .productsSold(new ArrayList<>())
            .build();

    sale = saleRepo.save(sale);

    var total = 0.0;

    for (var item : cart.items()) {
      var product =
          productRepo
              .findById(item.productId())
              .orElseThrow(() -> new SaleValidationException("Produto não encontrado"));

      var sp =
          SaleProductEntity.builder()
              .id(SaleProductId.builder().saleId(sale.getId()).productId(product.getId()).build())
              .saleEntity(sale)
              .product(product)
              .quantity(item.quantity())
              .unitPrice(product.getUnitPrice())
              .totalValue(product.getUnitPrice() * item.quantity())
              .build();

      sale.getProductsSold().add(sp);
      total += sp.getTotalValue();
    }

    sale = sale.toBuilder().totalValue(total).build();

    saleRepo.save(sale);

    return SaleDTO.builder()
        .id(sale.getId())
        .saleDate(sale.getSaleDate())
        .totalValue(sale.getTotalValue())
        .productsSold(
            sale.getProductsSold().stream()
                .map(
                    sp ->
                        SaleProductDTO.builder()
                            .productId(sp.getProduct().getId())
                            .name(sp.getProduct().getName())
                            .category(sp.getProduct().getCategory())
                            .unitPrice(sp.getUnitPrice())
                            .unitOfMeasure(sp.getProduct().getUnitOfMeasure())
                            .quantity(sp.getQuantity())
                            .build())
                .toList())
        .build();
  }

  public List<SaleDTO> findAllDTO() {
    return saleRepo.findAll().stream()
        .map(
            s ->
                SaleDTO.builder()
                    .id(s.getId())
                    .saleDate(s.getSaleDate())
                    .totalValue(s.getTotalValue())
                    .productsSold(
                        s.getProductsSold().stream()
                            .map(
                                sp ->
                                    SaleProductDTO.builder()
                                        .productId(sp.getProduct().getId())
                                        .name(sp.getProduct().getName())
                                        .category(sp.getProduct().getCategory())
                                        .unitPrice(sp.getProduct().getUnitPrice())
                                        .unitOfMeasure(sp.getProduct().getUnitOfMeasure())
                                        .quantity(sp.getQuantity())
                                        .build())
                            .toList())
                    .build())
        .toList();
  }

  public List<SaleDTO> filter(SaleFilterDTO f) {
    var all = findAllDTO();
    return all.stream()
        .filter(
            s -> {
              var dateOk = true;
              if (nonNull(f.fromDate()) && !f.fromDate().isBlank())
                dateOk &= !s.saleDate().toLocalDate().isBefore(LocalDate.parse(f.fromDate()));
              if (nonNull(f.toDate()) && !f.toDate().isBlank())
                dateOk &= !s.saleDate().toLocalDate().isAfter(LocalDate.parse(f.toDate()));
              var nameOk =
                  (isNull(f.name()) || f.name().isBlank())
                      || s.productsSold().stream()
                          .anyMatch(p -> p.name().toLowerCase().contains(f.name().toLowerCase()));
              var categoryOk =
                  (isNull(f.category()) || f.category().equals("Todas"))
                      || s.productsSold().stream()
                          .anyMatch(p -> p.category().name().equalsIgnoreCase(f.category()));
              return dateOk && nameOk && categoryOk;
            })
        .toList();
  }
}
