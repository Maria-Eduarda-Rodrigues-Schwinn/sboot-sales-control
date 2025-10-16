package com.sales_control.pi.service;

import static java.util.Objects.nonNull;

import com.sales_control.pi.dto.ProductDTO;
import com.sales_control.pi.dto.UpdateProductDTO;
import com.sales_control.pi.entity.ProductEntity;
import com.sales_control.pi.exception.ValidationException;
import com.sales_control.pi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repo;

  public List<ProductDTO> findAllDTO() {
    return repo.findAll().stream().map(this::toDTO).toList();
  }

  public List<ProductDTO> searchByName(String name) {
    return repo.findByNameContainingIgnoreCase(name).stream().map(this::toDTO).toList();
  }

  @Transactional
  public ProductDTO update(Integer id, UpdateProductDTO dto) {
    var p = repo.findById(id).orElseThrow(() -> new ValidationException("Produto não encontrado"));
    if (nonNull(dto.unitPrice())) {
      if (dto.unitPrice() < 0) throw new ValidationException("Preço inválido");
      p.setUnitPrice(dto.unitPrice());
    }
    if (nonNull(dto.quantity())) {
      if (dto.quantity() < 0) throw new ValidationException("Quantidade inválida");
      p.setQuantity(dto.quantity());
    }
    return toDTO(repo.save(p));
  }

  @Transactional
  public void delete(Integer id) {
    repo.deleteById(id);
  }

  private ProductDTO toDTO(ProductEntity p) {
    return ProductDTO.builder()
        .id(p.getId())
        .name(p.getName())
        .category(p.getCategory())
        .unitPrice(p.getUnitPrice())
        .unitOfMeasure(p.getUnitOfMeasure())
        .quantity(p.getQuantity())
        .build();
  }
}
