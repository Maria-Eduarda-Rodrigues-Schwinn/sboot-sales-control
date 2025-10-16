package com.sales_control.pi.service;

import static java.util.Objects.nonNull;

import com.sales_control.pi.dto.UpdateProductDTO;
import com.sales_control.pi.dto.response.ProductResponseDTO;
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

  public List<ProductResponseDTO> findAllDTO() {
    return repo.findAll().stream().map(this::toDTO).toList();
  }

  public List<ProductResponseDTO> searchByName(String name) {
    return repo.findByNameContainingIgnoreCase(name).stream().map(this::toDTO).toList();
  }

  @Transactional
  public ProductResponseDTO update(Integer id, UpdateProductDTO dto) {
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

  private ProductResponseDTO toDTO(ProductEntity p) {
    return ProductResponseDTO.builder()
        .id(p.getId())
        .name(p.getName())
        .category(p.getCategory().getTranslation())
        .unitPrice(p.getUnitPrice())
        .unitOfMeasure(p.getUnitOfMeasure().getTranslation())
        .quantity(p.getQuantity())
        .build();
  }
}
